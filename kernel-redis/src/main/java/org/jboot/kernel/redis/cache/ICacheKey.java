package org.jboot.kernel.redis.cache;
import org.jboot.kernel.toolkit.utils.ObjectUtil;
import org.jboot.kernel.toolkit.utils.StringPool;
import org.jboot.kernel.toolkit.utils.StringUtil;
import org.springframework.lang.Nullable;
import java.time.Duration;
public interface ICacheKey {
	/**
	 * 获取前缀
	 *
	 * @return key 前缀
	 */
	String getPrefix();
	/**
	 * 超时时间
	 *
	 * @return 超时时间
	 */
	@Nullable
	default Duration getExpire() {
		return null;
	}
	/**
	 * 组装 cache key
	 *
	 * @param suffix 参数
	 * @return cache key
	 */
	default CacheKey getKey(Object... suffix) {
		String prefix = this.getPrefix();
		// 拼接参数
		String key;
		if (ObjectUtil.isEmpty(suffix)) {
			key = prefix;
		} else {
			key = prefix.concat(StringUtil.join(suffix, StringPool.COLON));
		}
		Duration expire = this.getExpire();
		return expire == null ? new CacheKey(key) : new CacheKey(key, expire);
	}
}
