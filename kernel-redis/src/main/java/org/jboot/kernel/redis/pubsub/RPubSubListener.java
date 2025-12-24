package org.jboot.kernel.redis.pubsub;
import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RPubSubListener {
	/**
	 * topic name，支持通配符， 如 *、? 和 [...]
	 *
	 * @return String
	 */
	String value();
}
