package org.jboot.kernel.cloud.feign;
import feign.Target;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cloud.openfeign.FallbackFactory;
@AllArgsConstructor
public class JFallbackFactory<T> implements FallbackFactory<T> {
	private final Target<T> target;
	@Override
	@SuppressWarnings("unchecked")
	public T create(Throwable cause) {
		final Class<T> targetType = target.type();
		final String targetName = target.name();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetType);
		enhancer.setUseCache(true);
		enhancer.setCallback(new JFeignFallback<>(targetType, targetName, cause));
		return (T) enhancer.create();
	}
}
