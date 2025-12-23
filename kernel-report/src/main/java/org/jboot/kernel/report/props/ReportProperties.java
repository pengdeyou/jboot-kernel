
package org.jboot.kernel.report.props;

import lombok.Data;
import org.jboot.kernel.toolkit.utils.StringPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * UReport配置类
 *
 * @author Corsak
 */
@Data
@ConfigurationProperties(prefix = "report")
public class ReportProperties {
	private Boolean enabled = true;
	private Boolean auth = true;
	private Boolean disableHttpSessionReportCache = false;
	private Boolean disableFileProvider = true;
	private String fileStoreDir = StringPool.EMPTY;
	private Boolean debug = false;
}
