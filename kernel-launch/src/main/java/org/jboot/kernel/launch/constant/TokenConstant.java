
package org.jboot.kernel.launch.constant;

/**
 * Token配置常量.
 *
 * @author Corsak
 */
public interface TokenConstant {

	String AVATAR = "avatar";
	String HEADER = "token-auth";
	String BEARER = "bearer";
	String CRYPTO = "crypto";
	String ACCESS_TOKEN = "access_token";
	String REFRESH_TOKEN = "refresh_token";
	String TOKEN_TYPE = "token_type";
	String EXPIRES_IN = "expires_in";
	String ACCOUNT = "account";
	String USER_ID = "user_id";
	String ROLE_ID = "role_id";
	String DEPT_ID = "dept_id";
	String USER_NAME = "user_name";
	String ROLE_NAME = "role_name";
	String TENANT_ID = "tenant_id";
	String OAUTH_ID = "oauth_id";
	String CLIENT_ID = "client_id";
	String LICENSE = "license";
	String LICENSE_NAME = "powered by JBoot";
	String DEFAULT_AVATAR = "https://jboot.cn/images/logo-small.png";
	Integer AUTH_LENGTH = 7;

	/**
	 * token签名
	 */
	String SIGN_KEY = "jbootisapowerfulmicroservicearchitectureupgradedandoptimizedfromacommercialproject";
	/**
	 * key安全长度，具体见：https://tools.ietf.org/html/rfc7518#section-3.2
	 */
	int SIGN_KEY_LENGTH = 32;

}
