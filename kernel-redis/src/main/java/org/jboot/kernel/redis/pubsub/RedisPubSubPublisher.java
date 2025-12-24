package org.jboot.kernel.redis.pubsub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.redis.cache.JRedis;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.serializer.RedisSerializer;
@Slf4j
@RequiredArgsConstructor
public class RedisPubSubPublisher implements InitializingBean, RPubSubPublisher {
	private final JRedis jRedis;
	private final RedisSerializer<Object> redisSerializer;
	@Override
	public <T> Long publish(String channel, T message) {
		return jRedis.publish(channel, message, redisSerializer::serialize);
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("RPubSubPublisher init success.");
	}
}
