package com.redis_cache.redis_sentinel.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis_cache.redis_sentinel.configuration.GenericRedisValueOpsTemplate;
import com.redis_cache.redis_sentinel.model.IceCream;
import com.redis_cache.redis_sentinel.repository.IceCreamRepository;
import com.redis_cache.redis_sentinel.service.IceCreamDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersistentIceCreamDataManager implements IceCreamDataManager {

    private static final Logger logger = LoggerFactory.getLogger(PersistentIceCreamDataManager.class);

    private final String iceCreamKey = "ICE_CREAM_TABLE_";

    @Autowired
    private IceCreamRepository iceCreamRepository;

    @Autowired
    private GenericRedisValueOpsTemplate genericRedisValueOpsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<IceCream> getAllIceCream()
    {
        logger.info("finding all the records in ice-cream table");
        return iceCreamRepository.findAll();
    }

    @Override
    public IceCream getIceCream(String id)
    {
        logger.info("finding ice-cream record by id");
        if(genericRedisValueOpsTemplate.getMap(iceCreamKey+id) == null) {
            logger.info("the ice-cream is not present in redis cache found by id");
            Optional<IceCream> iceCream = iceCreamRepository.findById(id);
            if(iceCream.isPresent()){
                try {
                    genericRedisValueOpsTemplate
                            .putMapWithTimeOut(iceCreamKey+id, this.objectMapper.writeValueAsString(iceCream.get()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            return iceCream.orElse(null);
        } else {
            logger.info("the ice-cream found by id is present in redis cache");
            IceCream iceCream = null;
            try {
                iceCream = this.objectMapper
                        .readValue(genericRedisValueOpsTemplate.getMap(iceCreamKey+id), IceCream.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return iceCream;
        }
    }

    @Override
    public IceCream addIceCream(IceCream iceCream)
    {
        logger.info("Adding new ice-cream record in table");
        try {
            // adding the new ice-cream record
            genericRedisValueOpsTemplate.putMap(iceCreamKey+iceCream.getId(), this.objectMapper.writeValueAsString(iceCream));
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return iceCreamRepository.save(iceCream);
    }

    @Override
    public IceCream updateCream(IceCream iceCream, String id)
    {
        logger.info("Updating existing ice-cream record in ice-cream table");
        iceCreamRepository.deleteById(id);
        try {
            // updating the ice-cream record with given id in redis cache
            if (genericRedisValueOpsTemplate.getMap(iceCreamKey+id) != null)
                genericRedisValueOpsTemplate.putMapWithTimeOut(iceCreamKey+id, this.objectMapper.writeValueAsString(iceCream));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return iceCreamRepository.save(iceCream);
    }

    @Override
    public void deleteIceCream(String id)
    {
        logger.info("Delete the existing ice-cream record in ice-cream table by id");
        iceCreamRepository.deleteById(id);
        // deleting the topic record with given id in redis cache
        if(genericRedisValueOpsTemplate.getMap(iceCreamKey+id) != null)
            genericRedisValueOpsTemplate.putMapWithTimeOut(iceCreamKey+id, null);
    }
}
