//package br.com.blocker.blockersapi.redis;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import br.com.blocker.blockersapi.entity.ip.Ip;
//
//@Configuration
//public class RedisConfiguration {
//	
//	@Bean
//	ReactiveRedisOperations<String, Ip> redisOperations(ReactiveRedisConnectionFactory factory) {
//		Jackson2JsonRedisSerializer<Ip> serializer = new Jackson2JsonRedisSerializer<>(Ip.class);
//
//		RedisSerializationContext.RedisSerializationContextBuilder<String, Ip> builder =
//				RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//
//		RedisSerializationContext<String, Ip> context = builder.value(serializer).build();
//
//		return new ReactiveRedisTemplate<>(factory, context);
//	}	
//}
