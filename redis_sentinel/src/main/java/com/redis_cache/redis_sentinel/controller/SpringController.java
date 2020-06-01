package com.redis_cache.redis_sentinel.controller;

import com.redis_cache.redis_sentinel.model.Topic;
import com.redis_cache.redis_sentinel.service.TopicDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/springBootJpa")
public class SpringController {
	
	@Autowired
	private TopicDataManager topicDataManager;

	@RequestMapping(value="/",method= RequestMethod.GET, produces = "text/plain")
	public ResponseEntity<String> helloMethod()
	{
		return new ResponseEntity<>("Hello World", HttpStatus.OK);
	}

	@RequestMapping(value="/topics", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Topic>> listOfTopcs()
	{
		return new ResponseEntity<List<Topic>>(topicDataManager.getAllTopics(), HttpStatus.OK);
	}

	@RequestMapping(value="/topics/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Topic> getRequiredTopic(@PathVariable String id)
	{
		Optional<Topic> resultTopic = topicDataManager.getTopic(id);
		if(resultTopic != null) {
			return new ResponseEntity<Topic>(resultTopic.get(), HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	//In this json object is sent 
	/*
	 * {
	 * 		"id":"java"
	 * 		"name":"java programming"
	 * 		"description":"java is easy"
	 * }
	 */
	@RequestMapping(value="/topics/add",method= RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addTopic(@RequestBody Topic topic)
	{
		topicDataManager.addTopic(topic);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value="/topics/update/{id}",method= RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateTopic(@RequestBody Topic topic, @PathVariable String id)
	{
		topicDataManager.updateTopic(topic,id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//Url "localhost:8080/springBootJpa/topics/delete/java
	@RequestMapping(value="/topics/delete/{id}",method= RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteTopic(@PathVariable String id)
	{
		topicDataManager.deleteTopic(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//Url "localhost:8080/springBootJpa/topics/getById?id=java
	@RequestMapping(value="/topics/getById",method= RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Topic> getById(@RequestParam(value="id")String id)
	{
		Topic resultTopic = topicDataManager.getById(id);
		if(resultTopic != null) {
			return new ResponseEntity<Topic>(resultTopic, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/topics/getByIdAndName",method= RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Topic> getByIdAndName(@RequestParam(value="id")String id, @RequestParam(value="name")String name)
	{
		Topic resultTopic = topicDataManager.getByIdAndName(id, name);
		if(resultTopic != null) {
			return new ResponseEntity<Topic>(resultTopic, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
