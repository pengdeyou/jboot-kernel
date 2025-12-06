
package org.jboot.kernel.tool.sensitive;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 敏感词分组枚举.
 *
 * @author BladeX
 */
@Getter
public enum SensitiveWord {

	// 安全敏感词组
	SECURE(Arrays.asList(
		// 认证信息类
		"password", "pwd", "token", "secret", "bearer", "key",
		// API相关
		"api_key", "access_token", "refresh_token", "auth_token",
		// 加密相关
		"private_key", "public_key", "salt", "hash",
		// 安全相关
		"security", "certificate", "credentials",
		// 数据库相关
		"connection_string", "jdbc", "sql", "database_url"
	)),

	// 身份验证相关敏感词
	AUTHENTICATION(Arrays.asList(
		"otp", "verification_code", "auth_code", "mfa_token"
	));

	private final List<String> words;

	SensitiveWord(List<String> words) {
		this.words = Collections.unmodifiableList(words);
	}
}
