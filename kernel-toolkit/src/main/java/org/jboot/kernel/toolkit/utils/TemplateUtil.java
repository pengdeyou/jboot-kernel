
package org.jboot.kernel.toolkit.utils;


import org.jboot.kernel.toolkit.support.Kv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模版解析工具类
 */
public class TemplateUtil {

	/**
	 * 支持 ${} 与 #{} 两种模版占位符
	 */
	private static final Pattern pattern = Pattern.compile("\\$\\{([^{}]+)}|\\#\\{([^{}]+)}");

	/**
	 * 解析模版
	 *
	 * @param template 模版
	 * @param params   参数
	 * @return 解析后的字符串
	 */
	public static String process(String template, Kv params) {
		Matcher matcher = pattern.matcher(template);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String key = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
			String replacement = params.getStr(key);
			if (replacement == null) {
				throw new IllegalArgumentException("参数中缺少必要的键: " + key);
			}
			matcher.appendReplacement(sb, replacement);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 解析模版
	 *
	 * @param template 模版
	 * @param params   参数
	 * @return 解析后的字符串
	 */
	public static String safeProcess(String template, Kv params) {
		Matcher matcher = pattern.matcher(template);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String key = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
			String replacement = params.getStr(key);
			if (replacement != null) {
				matcher.appendReplacement(sb, replacement);
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
