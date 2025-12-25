
package org.jboot.kernel.report.entity;

import org.jbatis.rds.annotation.IdType;
import org.jbatis.rds.annotation.TableId;
import org.jbatis.rds.annotation.TableLogic;
import org.jbatis.rds.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * UReport实体类
 *
 * @author Corsak
 */
@Data
@TableName("tb_report_file")
public class ReportFileEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 文件内容
	 */
	private byte[] content;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否已删除
	 */
	@TableLogic
	private Integer isDeleted;

}
