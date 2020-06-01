package com.redis_cache.redis_sentinel.service;

import com.redis_cache.redis_sentinel.model.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicDataManager {
	
	List<Topic> getAllTopics();
	Optional<Topic> getTopic(String id);
	Topic addTopic(Topic topic);
	Topic updateTopic(Topic topic, String id);
	void deleteTopic(String id);
	Topic getById(String id);
	Topic getByIdAndName(String id,String name);
}
