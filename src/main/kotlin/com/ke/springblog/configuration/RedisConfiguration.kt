package com.ke.springblog.configuration

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableCaching//开启缓存
@Configuration
class RedisConfiguration {


	@Bean
	fun cacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager {

		return RedisCacheManager.builder(redisConnectionFactory).build()
	}

	@Bean
	fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<Any, Any> {


		return RedisTemplate<Any, Any>()
				.apply {

					setConnectionFactory(redisConnectionFactory)
					//使用jdk 自带 序列化
					val jdkSerializationRedisSerializer = JdkSerializationRedisSerializer()
					valueSerializer = jdkSerializationRedisSerializer
					hashValueSerializer = jdkSerializationRedisSerializer

					val stringRedisSerializer = StringRedisSerializer()
					keySerializer = stringRedisSerializer
					hashKeySerializer = stringRedisSerializer

					afterPropertiesSet()
				}

	}
}