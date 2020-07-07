package com.redis_cache.redis_sentinel.repository;

import com.redis_cache.redis_sentinel.model.IceCream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IceCreamRepository extends JpaRepository<IceCream, String> {
}
