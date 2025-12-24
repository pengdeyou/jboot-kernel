package org.jboot.kernel.redis.pubsub;
public interface RPubSubPublisher {
	/**
	 * 发布消息
	 *
	 * @param channel 队列名
	 * @param message 消息
	 * @return 收到消息的客户数量
	 */
	<T> Long publish(String channel, T message);
}
