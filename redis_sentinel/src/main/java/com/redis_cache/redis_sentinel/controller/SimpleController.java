package com.redis_cache.redis_sentinel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/simpleController")
public class SimpleController {

	@RequestMapping(value="/helloFriends",method= RequestMethod.GET, produces = "text/plain")
	public ResponseEntity<String> helloFriendsMethod()
	{
		return ResponseEntity.ok().body("hello friends");
	}
}
