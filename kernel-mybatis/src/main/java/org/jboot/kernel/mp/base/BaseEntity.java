
package org.jboot.kernel.mp.base;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jboot.kernel.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 *
 * @author Corsak
 */
@Data
public class BaseEntity implements Serializable {

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 创建人
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "创建人", hidden = true)
	private Long createUser;

	/**
	 * 创建部门
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "创建部门", hidden = true)
	private Long createDept;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@Schema(description = "创建时间", hidden = true)
	private Date createTime;

	/**
	 * 更新人
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "更新人", hidden = true)
	private Long updateUser;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@Schema(description = "更新时间", hidden = true)
	private Date updateTime;

	/**
	 * 状态[1:正常]
	 */
	@Schema(description = "业务状态", hidden = true)
	private Integer status;

	/**
	 * 状态[0:未删除,1:删除]
	 */
	@TableLogic
	@Schema(description = "是否已删除", hidden = true)
	private Integer isDeleted;
}
