package org.jboot.kernel.redis.pubsub;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@RequiredArgsConstructor
public class RPubSubEvent<M> {
	/**
	 * 匹配模式时的正则
	 */
	private final CharSequence pattern;
	/**
	 * channel
	 */
	private final CharSequence channel;
	/**
	 * pub 的消息对象
	 */
	private final M msg;
}
