
package org.jboot.kernel.oss.rule;

import lombok.AllArgsConstructor;
import org.jboot.kernel.tool.utils.DateUtil;
import org.jboot.kernel.tool.utils.FileUtil;
import org.jboot.kernel.tool.utils.StringPool;
import org.jboot.kernel.tool.utils.StringUtil;

/**
 * 默认存储桶生成规则
 *
 * @author Corsak
 */
@AllArgsConstructor
public class JOssRule implements OssRule {

	@Override
	public String bucketName(String bucketName) {
		return bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
