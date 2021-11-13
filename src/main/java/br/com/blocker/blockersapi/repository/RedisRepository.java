package br.com.blocker.blockersapi.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class RedisRepository {

	@Autowired private ReactiveRedisConnectionFactory factory;
	@Autowired private ReactiveRedisOperations<String, String> operations;
}
