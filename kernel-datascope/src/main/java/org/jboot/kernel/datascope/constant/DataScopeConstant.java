
package org.jboot.kernel.datascope.constant;

import java.util.StringJoiner;

/**
 * 数据权限常量
 *
 * @author Corsak
 */
public interface DataScopeConstant {

	String DEFAULT_COLUMN = "create_dept";

	/**
	 * 获取部门数据
	 */
	String DATA_BY_DEPT = "select id from tb_dept where ancestors like concat(concat('%', ?),'%') and is_deleted = 0";

	/**
	 * 根据resourceCode获取数据权限配置
	 */
	String DATA_BY_CODE = "select resource_code, scope_column, scope_field, scope_type, scope_value from tb_scope_data where resource_code = ?";

	/**
	 * 根据mapperId获取数据权限配置
	 *
	 * @param size 数量
	 * @return String
	 */
	static String dataByMapper(int size) {
		return "select resource_code, scope_column, scope_field, scope_type, scope_value from tb_scope_data where scope_class = ? and id in (select scope_id from tb_role_scope where role_id in (" + buildHolder(size) + "))";
	}

	/**
	 * 获取Sql占位符
	 *
	 * @param size 数量
	 * @return String
	 */
	static String buildHolder(int size) {
		StringJoiner joiner = new StringJoiner(",");
		for (int i = 0; i < size; i++) {
			joiner.add("?");
		}
		return joiner.toString();
	}

}
