
package org.jboot.kernel.toolkit.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


/**
 * 森林节点类
 *
 * @author smallchill
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ForestNode extends BaseNode<ForestNode> {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 节点内容
	 */
	private Object content;

	public ForestNode(Long id, Long parentId, Object content) {
		this.id = id;
		this.parentId = parentId;
		this.content = content;
	}

}
