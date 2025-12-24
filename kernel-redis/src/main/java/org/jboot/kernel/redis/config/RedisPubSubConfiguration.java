package org.jboot.kernel.redis.config;
import org.jboot.kernel.redis.cache.JRedis;
import org.jboot.kernel.redis.pubsub.RPubSubListenerDetector;
import org.jboot.kernel.redis.pubsub.RPubSubListenerLazyFilter;
import org.jboot.kernel.redis.pubsub.RPubSubPublisher;
import org.jboot.kernel.redis.pubsub.RedisPubSubPublisher;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
@AutoConfiguration
public class RedisPubSubConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}
	@Bean
	public RPubSubPublisher topicEventPublisher(JRedis jRedis,
												RedisSerializer<Object> redisSerializer) {
		return new RedisPubSubPublisher(jRedis, redisSerializer);
	}
	@Bean
	public static RPubSubListenerDetector topicListenerDetector(ApplicationContext applicationContext) {
		return new RPubSubListenerDetector(applicationContext);
	}
	@Bean
	public RPubSubListenerLazyFilter rPubSubListenerLazyFilter() {
		return new RPubSubListenerLazyFilter();
	}
}
