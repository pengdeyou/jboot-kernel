
package org.jboot.kernel.secure.provider;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 客户端详情
 *
 * @author Chill
 */
@Data
public class ClientDetails implements IClientDetails {

	/**
	 * 客户端id
	 */
	@Schema(description = "客户端id")
	private String clientId;
	/**
	 * 客户端密钥
	 */
	@Schema(description = "客户端密钥")
	private String clientSecret;

	/**
	 * 令牌过期秒数
	 */
	@Schema(description = "令牌过期秒数")
	private Integer accessTokenValidity;
	/**
	 * 刷新令牌过期秒数
	 */
	@Schema(description = "刷新令牌过期秒数")
	private Integer refreshTokenValidity;

}
