
package org.jboot.kernel.secure.props;

import io.jsonwebtoken.JwtException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.launch.constant.TokenConstant;
import org.jboot.kernel.tool.utils.StringPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * secure放行额外配置
 *
 * @author Corsak
 */
@Slf4j
@Data
@ConfigurationProperties("jboot.kernel.token")
public class JTokenProperties {

	/**
	 * token签名
	 */
	private String signKey = StringPool.EMPTY;

	/**
	 * token签名
	 */
	private String aesKey = StringPool.EMPTY;

	/**
	 * 获取签名规则
	 */
	public String getSignKey() {
		if (this.signKey.length() < TokenConstant.SIGN_KEY_LENGTH) {
			throw new JwtException("请配置 jboot.kernel.token.sign-key 的值, 长度32位以上");
		}
		return this.signKey;
	}

}
