
package org.jboot.kernel.report.endpoint;

import org.jbatis.rds.kernel.metadata.IPage;
import lombok.AllArgsConstructor;
import org.jboot.kernel.rds.support.Condition;
import org.jboot.kernel.rds.support.Query;
import org.jboot.kernel.report.entity.ReportFileEntity;
import org.jboot.kernel.report.service.IReportFileService;
import org.jboot.kernel.toolkit.api.R;
import org.jboot.kernel.toolkit.utils.Func;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.Map;

/**
 * UReport API端点
 *
 * @author Corsak
 */
@Hidden
@RestController
@AllArgsConstructor
@RequestMapping("/report/rest")
public class ReportEndpoint {

	private final IReportFileService service;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	public R<ReportFileEntity> detail(ReportFileEntity file) {
		ReportFileEntity detail = service.getOne(Condition.getQueryWrapper(file));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	public R<IPage<ReportFileEntity>> list(@RequestParam Map<String, Object> file, Query query) {
		IPage<ReportFileEntity> pages = service.page(Condition.getPage(query), Condition.getQueryWrapper(file, ReportFileEntity.class));
		return R.data(pages);
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	public R remove(@RequestParam String ids) {
		boolean temp = service.removeByIds(Func.toLongList(ids));
		return R.status(temp);
	}

}
