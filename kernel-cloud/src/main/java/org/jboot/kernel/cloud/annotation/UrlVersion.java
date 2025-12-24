package org.jboot.kernel.cloud.annotation;
import java.lang.annotation.*;
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UrlVersion {
	/**
	 * url 路径中的版本
	 *
	 * @return 版本号
	 */
	String value() default "";
}
