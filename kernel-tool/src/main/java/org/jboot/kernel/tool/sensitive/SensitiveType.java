
package org.jboot.kernel.tool.sensitive;

import java.util.regex.Pattern;

/**
 * 脱敏类型枚举.
 *
 * @author JBoot Kernel
 */
public enum SensitiveType {
	GLOBAL("全局", "(.{2}).*(.{2})", "$1****$2"),
	MOBILE("手机号", "(\\d{3})\\d{4}(\\d{4})", "$1****$2"),
	EMAIL("电子邮箱", "(\\w{2})\\w+(@\\w+\\.\\w+)", "$1****$2"),
	ID_CARD("身份证号", "(\\d{4})\\d{10}(\\w{4})", "$1**********$2"),
	BANK_CARD("银行卡号", "(\\d{4})\\d+(\\d{4})", "$1****$2"),
	IP_ADDRESS("IP地址", "(\\d{1,3}\\.\\d{1,3})\\.\\d{1,3}\\.\\d{1,3}", "$1.***.***"),
	MAC_ADDRESS("MAC地址", "([0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}):[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}:[0-9A-Fa-f]{2}", "$1:****"),
	;

	private final String replacement;
	private final Pattern pattern;

	SensitiveType(String ignore, String regex, String replacement) {
		this.replacement = replacement;
		this.pattern = Pattern.compile(regex);
	}

	/**
	 * 替换文本
	 * @param content content
	 * @return 替换后的内容
	 */
	public String replaceAll(String content) {
		return this.pattern.matcher(content).replaceAll(this.replacement);
	}
}
