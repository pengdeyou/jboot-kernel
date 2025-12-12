
package org.jboot.kernel.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * secure放行额外配置
 *
 * @author Corsak
 */
@Data
@ConfigurationProperties("jboot.kernel.auth")
public class JAuthProperties {

	/**
	 * sm2公钥
	 */
	private String publicKey;

	/**
	 * sm2私钥
	 */
	private String privateKey;

}
