package com.redis_cache.redis_sentinel.serviceImpl;

import com.redis_cache.redis_sentinel.configuration.GenericRedisTemplate;
import com.redis_cache.redis_sentinel.model.Topic;
import com.redis_cache.redis_sentinel.repository.TopicRepository;
import com.redis_cache.redis_sentinel.service.TopicDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PersistentTopicDataManager implements TopicDataManager {

	private static final Logger logger = LoggerFactory.getLogger(PersistentTopicDataManager.class);

	private final String topicRedisKey = "TOPIC_TABLE";

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private GenericRedisTemplate genericRedisTemplate;

	// TODO : Also define object mapper here.


	public List<Topic> getAllTopics()
	{
		logger.info("finding all the records in topic table");
		return topicRepository.findAll();
	}
	public Optional<Topic> getTopic(String id)
	{
		logger.info("finding topic record by id");
		return topicRepository.findById(id);
	}
	public Topic addTopic(Topic topic)
	{
		logger.info("Adding new topic record in table");
		return topicRepository.save(topic);
	}
	public Topic updateTopic(Topic topic, String id)
	{
		logger.info("Updating existing topic record in topic table");
		topicRepository.deleteById(id);
		return topicRepository.save(topic);
	}
	public void deleteTopic(String id)
	{
		logger.info("Delete the existing topic record in topic table by id");
		topicRepository.deleteById(id);
	}
	
	public Topic getById(String id)
	{
		logger.info("finding topic record by id");
		return topicRepository.getById(id);
	}
	
	public Topic getByIdAndName(String id,String name)
	{
		logger.info("finding topic record by id and name");
		return topicRepository.getByIdAndName(id, name);
	}
	
}
