package org.jboot.kernel.crypto.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jboot.kernel.crypto.enums.CryptoType;

/**
 * <p>加密注解信息</p>
 *
 * @author licoy.cn, Corsak
 */
@Getter
@RequiredArgsConstructor
public class CryptoInfoBean {

	/**
	 * 加密类型
	 */
	private final CryptoType type;
	/**
	 * 私钥
	 */
	private final String secretKey;

}
