
package org.jboot.kernel.report.endpoint;

import org.jboot.kernel.launch.constant.AppConstant;
import org.jboot.kernel.report.service.IReportFileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

/**
 * UReport Boot版 API端点
 *
 * @author Chill
 */
@Hidden
@RestController
@RequestMapping(AppConstant.APPLICATION_REPORT_NAME + "/report/rest")
public class ReportBootEndpoint extends ReportEndpoint {

	public ReportBootEndpoint(IReportFileService service) {
		super(service);
	}

}
