
package org.jboot.kernel.launch.constant;

/**
 * Nacos常量.
 *
 * @author Corsak
 */
public interface NacosConstant {

	/**
	 * optional
	 */
	String OPTIONAL = "optional";

	/**
	 * nacos
	 */
	String NACOS = "nacos";

	/**
	 * nacos 地址
	 */
	String NACOS_ADDR = "127.0.0.1:8848";

	/**
	 * nacos 配置前缀
	 */
	String NACOS_CONFIG_PREFIX = "blade";

	/**
	 * nacos 组配置后缀
	 */
	String NACOS_GROUP_SUFFIX = "-group";

	/**
	 * nacos 配置文件类型
	 */
	String NACOS_CONFIG_FORMAT = "yaml";

	/**
	 * nacos json配置文件类型
	 */
	String NACOS_CONFIG_JSON_FORMAT = "json";

	/**
	 * nacos 是否刷新
	 */
	String NACOS_CONFIG_REFRESH = "true";

	/**
	 * nacos 分组
	 */
	String NACOS_CONFIG_GROUP = "DEFAULT_GROUP";

	/**
	 * 服务默认加载的配置
	 *
	 * @return dataId
	 */
	static String dataId() {
		return OPTIONAL + ":" + NACOS + ":" + NACOS_CONFIG_PREFIX + "." + NACOS_CONFIG_FORMAT;
	}

	/**
	 * 服务默认加载的配置
	 *
	 * @param profile 环境变量
	 * @return dataId
	 */
	static String dataId(String profile) {
		return OPTIONAL + ":" + NACOS + ":" + NACOS_CONFIG_PREFIX + "-" + profile + "." + NACOS_CONFIG_FORMAT;
	}

	/**
	 * 构建服务对应的 dataId
	 *
	 * @param appName 服务名
	 * @param profile 环境变量
	 * @return dataId
	 */
	static String dataId(String appName, String profile) {
		return dataId(appName, profile, NACOS_CONFIG_FORMAT);
	}

	/**
	 * 构建服务对应的 dataId
	 *
	 * @param appName 服务名
	 * @param profile 环境变量
	 * @param format  文件类型
	 * @return dataId
	 */
	static String dataId(String appName, String profile, String format) {
		return OPTIONAL + ":" + NACOS + ":" + appName + "-" + profile + "." + format;
	}

}
