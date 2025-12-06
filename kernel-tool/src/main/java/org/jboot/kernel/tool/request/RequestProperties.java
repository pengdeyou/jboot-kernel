
package org.jboot.kernel.tool.request;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Request配置类
 *
 * @author Chill
 */
@Data
@ConfigurationProperties("blade.request")
public class RequestProperties {

	/**
	 * 开启自定义request
	 */
	private Boolean enabled = true;

	/**
	 * 放行url
	 */
	private List<String> skipUrl = new ArrayList<>();

	/**
	 * 禁用url
	 */
	private List<String> blockUrl = new ArrayList<>();

	/**
	 * 白名单，支持通配符，例如：10.20.0.8*、10.20.0.*
	 */
	private List<String> whiteList = new ArrayList<>();

	/**
	 * 黑名单，支持通配符，例如：10.20.0.8*、10.20.0.*
	 */
	private List<String> blackList = new ArrayList<>();

}
