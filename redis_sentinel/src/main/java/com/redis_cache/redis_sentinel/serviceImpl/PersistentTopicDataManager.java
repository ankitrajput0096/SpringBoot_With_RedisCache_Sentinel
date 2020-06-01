package com.redis_cache.redis_sentinel.serviceImpl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class PersistentTopicDataManager implements TopicDataManager {

	private static final Logger logger = LoggerFactory.getLogger(PersistentTopicDataManager.class);

	private final String topicRedisKey = "TOPIC_TABLE";

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private GenericRedisTemplate genericRedisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public List getAllTopics()
	{
		logger.info("finding all the records in topic table");
		if(genericRedisTemplate.getMap(topicRedisKey, "allTopics") == null) {
			logger.info("the all topics value is not present in redis cache");
			List<Topic> allTopics = topicRepository.findAll();
			try {
				genericRedisTemplate
						.putMap(topicRedisKey, "allTopics", this.objectMapper.writeValueAsString(allTopics));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return allTopics;
		} else {
			logger.info("the all topics value is present in redis cache");
			try {
				return this.objectMapper
						.readValue(genericRedisTemplate.getMap(topicRedisKey, "allTopics"), List.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	public Topic getTopic(String id)
	{
		logger.info("finding topic record by id");
		if(genericRedisTemplate.getMap(topicRedisKey, id) == null) {
			logger.info("the topic is not present in redis cache found by id");
			Optional<Topic> topic = topicRepository.findById(id);
			if(!topic.isPresent()) {
				genericRedisTemplate
						.putMap(topicRedisKey, id, null);
			}
			else {
				try {
					genericRedisTemplate
							.putMap(topicRedisKey, id, this.objectMapper.writeValueAsString(topic.get()));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
			return topic.orElse(null);
		} else {

			logger.info("the topic found by id is present in redis cache");
			Topic topic = null;
			try {
				topic = this.objectMapper
						.readValue(genericRedisTemplate.getMap(topicRedisKey, id), Topic.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return topic;
		}
	}
	public Topic addTopic(Topic topic)
	{
		logger.info("Adding new topic record in table");

		// deleting the alltopics in redis cache if present
		if(genericRedisTemplate.getMap(topicRedisKey, "allTopics") != null)
			genericRedisTemplate.deleteMap(topicRedisKey, "allTopics");

		return topicRepository.save(topic);
	}
	public Topic updateTopic(Topic topic, String id)
	{
		logger.info("Updating existing topic record in topic table");
		topicRepository.deleteById(id);

		// deleting the topic record with given id in redis cache
		if(genericRedisTemplate.getMap(topicRedisKey, id) != null)
			genericRedisTemplate.deleteMap(topicRedisKey, id);

		// deleting the alltopics in redis cache if present
		if(genericRedisTemplate.getMap(topicRedisKey, "allTopics") != null)
			genericRedisTemplate.deleteMap(topicRedisKey, "allTopics");

		return topicRepository.save(topic);
	}
	public void deleteTopic(String id)
	{
		logger.info("Delete the existing topic record in topic table by id");
		topicRepository.deleteById(id);

		// deleting the topic record with given id in redis cache
		if(genericRedisTemplate.getMap(topicRedisKey, id) != null)
			genericRedisTemplate.deleteMap(topicRedisKey, id);

		// deleting the alltopics in redis cache if present
		if(genericRedisTemplate.getMap(topicRedisKey, "allTopics") != null)
			genericRedisTemplate.deleteMap(topicRedisKey, "allTopics");

	}
	
	public Topic getById(String id)
	{
		logger.info("finding topic record by id");
		if(genericRedisTemplate.getMap(topicRedisKey, id) == null) {
			logger.info("the topic is not present in redis cache found by id");
			Topic topic = topicRepository.getById(id);
			if(topic == null) {
				genericRedisTemplate
						.putMap(topicRedisKey, id, null);
			}
			else {
				try {
					genericRedisTemplate
							.putMap(topicRedisKey, id, this.objectMapper.writeValueAsString(topic));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
			return topic;
		} else {
			logger.info("the topic found by id is present in redis cache");
			Topic topic = null;
			try {
				topic = this.objectMapper
						.readValue(genericRedisTemplate.getMap(topicRedisKey, id), Topic.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return topic;
		}
	}
	
	public Topic getByIdAndName(String id,String name)
	{
		logger.info("finding topic record by id and name");
		if(genericRedisTemplate.getMap(topicRedisKey, id+"_"+name) == null) {
			logger.info("the topic is not present in redis cache found by id and name");
			Topic topic = topicRepository.getByIdAndName(id, name);
			if(topic == null) {
				genericRedisTemplate
						.putMap(topicRedisKey, id+"_"+name, null);
			}
			else {
				try {
					genericRedisTemplate
							.putMap(topicRedisKey, id+"_"+name, this.objectMapper.writeValueAsString(topic));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
			return topic;
		} else {
			logger.info("the topic found by id and name is present in redis cache");
			Topic topic = null;
			try {
				topic = this.objectMapper
						.readValue(genericRedisTemplate.getMap(topicRedisKey, id+"_"+name), Topic.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return topic;
		}
	}
}
