
package org.jboot.kernel.log.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 异步配置
 *
 * @author Chill
 */
@Getter
@Setter
@ConfigurationProperties(BladeLogProperties.PREFIX)
public class BladeLogProperties {
	/**
	 * 前缀
	 */
	public static final String PREFIX = "blade.log";

	/**
	 * 是否开启 api 日志
	 */
	private Boolean api = Boolean.TRUE;
	/**
	 * 是否开启 error 日志
	 */
	private Boolean error = Boolean.TRUE;
	/**
	 * 是否开启 usual 日志
	 */
	private Boolean usual = Boolean.TRUE;
}
