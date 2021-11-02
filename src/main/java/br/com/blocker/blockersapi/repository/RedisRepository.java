//package br.com.blocker.blockersapi.repository;
//
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.stereotype.Repository;
//
//import reactor.core.publisher.Mono;
//
//@Repository
//public class RedisRepository {
//
//	private final ReactiveRedisOperations<String, String> operations;
//
//    public RedisRepository(ReactiveRedisOperations<String, String> operations) {
//        this.operations = operations;
//    }
//    
//    public Mono<Boolean> save(String s) {
//        
//        return operations.opsForValue()
//                .set("AAA", "BBB");
//    }
//}
