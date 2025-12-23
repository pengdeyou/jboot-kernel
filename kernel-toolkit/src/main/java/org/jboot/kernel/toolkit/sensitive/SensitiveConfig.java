
package org.jboot.kernel.toolkit.sensitive;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 敏感信息处理配置类
 *
 * @author JBoot Kernel
 */
@Builder
@Data
public class SensitiveConfig {
	// 启用的内置正则脱敏类型
	private Set<SensitiveType> sensitiveTypes;

	// 启用的内置敏感词分组
	private Set<SensitiveWord> sensitiveWords;

	// 自定义敏感词列表
	private List<String> customSensitiveWords;

	// 自定义正则表达式脱敏规则
	private Map<String, Pattern> customPatterns;

	// 自定义替换文本（可选，有默认值）
	private String replacement;

	// 是否按行处理
	private boolean processLineByLine;
}
