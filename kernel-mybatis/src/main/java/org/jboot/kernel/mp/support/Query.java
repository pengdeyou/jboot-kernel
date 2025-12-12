
package org.jboot.kernel.mp.support;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

/**
 * 分页工具
 *
 * @author Corsak
 */
@Data
@Accessors(chain = true)
@Schema(description = "查询条件")
public class Query {

	/**
	 * 当前页
	 */
	@Schema(description = "当前页")
	private Integer current;

	/**
	 * 每页的数量
	 */
	@Schema(description = "每页的数量")
	private Integer size;

	/**
	 * 排序的字段名
	 */
	@Schema(accessMode = READ_ONLY)
	private String ascs;

	/**
	 * 排序方式
	 */
	@Schema(accessMode = READ_ONLY)
	private String descs;

}
