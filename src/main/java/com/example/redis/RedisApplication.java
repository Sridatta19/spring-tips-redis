package com.example.redis;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Log
@SpringBootApplication
public class RedisApplication {

	private static class Cat {}

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<String, Cat> redisTemplate(
			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		RedisTemplate<String, Cat> template = new RedisTemplate<>();


		RedisSerializer<Cat> value = new Jackson2JsonRedisSerializer<Cat>(Cat.class);
		RedisSerializer keys = new StringRedisSerializer();

		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(keys);
		template.setHashKeySerializer(keys);
		template.setValueSerializer(value);
		template.setHashValueSerializer(value);

		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
}
