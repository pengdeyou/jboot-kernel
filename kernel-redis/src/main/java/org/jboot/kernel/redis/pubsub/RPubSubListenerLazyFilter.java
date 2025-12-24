package org.jboot.kernel.redis.pubsub;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.LazyInitializationExcludeFilter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
public class RPubSubListenerLazyFilter implements LazyInitializationExcludeFilter {
	@Override
	public boolean isExcluded(String beanName, BeanDefinition beanDefinition, Class<?> beanType) {
		// 类上有注解的情况
		RPubSubListener subscribe = AnnotationUtils.findAnnotation(beanType, RPubSubListener.class);
		if (subscribe != null) {
			return true;
		}
		// 方法上的注解
		List<Method> methodList = new ArrayList<>();
		ReflectionUtils.doWithMethods(beanType, method -> {
			RPubSubListener clientSubscribe = AnnotationUtils.findAnnotation(method, RPubSubListener.class);
			if (clientSubscribe != null) {
				methodList.add(method);
			}
		}, ReflectionUtils.USER_DECLARED_METHODS);
		return !methodList.isEmpty();
	}
}
