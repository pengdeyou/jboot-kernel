package org.jboot.kernel.secure;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

/**
 * 用户实体
 *
 * @author Corsak
 */
@Data
public class AuthUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	@Schema(accessMode = READ_ONLY)
	private String clientId;

	/**
	 * 用户id
	 */
	@Schema(accessMode = READ_ONLY)
	private Long userId;
	/**
	 * 租户ID
	 */
	@Schema(accessMode = READ_ONLY)
	private String tenantId;
	/**
	 * 部门id
	 */
	@Schema(accessMode = READ_ONLY)
	private String deptId;
	/**
	 * 昵称
	 */
	@Schema(accessMode = READ_ONLY)
	private String userName;
	/**
	 * 账号
	 */
	@Schema(accessMode = READ_ONLY)
	private String account;
	/**
	 * 角色id
	 */
	@Schema(accessMode = READ_ONLY)
	private String roleId;
	/**
	 * 角色名
	 */
	@Schema(accessMode = READ_ONLY)
	private String roleName;

}