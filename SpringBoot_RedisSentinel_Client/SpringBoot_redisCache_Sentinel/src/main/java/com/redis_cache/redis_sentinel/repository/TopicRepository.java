package com.redis_cache.redis_sentinel.repository;


import com.redis_cache.redis_sentinel.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository
		extends JpaRepository<Topic,String> {
	
	//This is query is know as JPQL (Java Persistent Query Language)
	@Query("SELECT top FROM Topic top WHERE top.id=:id")
	public Topic getById(@Param("id") String id);
	
	@Query("SELECT t FROM Topic t WHERE t.id=:id AND t.name=:name")
	public Topic getByIdAndName(
			@Param("id") String id,
			@Param("name") String name);
}
