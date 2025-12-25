
package org.jboot.kernel.report.service.impl;

import org.jbatis.rds.extension.service.impl.ServiceImpl;
import org.jboot.kernel.report.entity.ReportFileEntity;
import org.jboot.kernel.report.mapper.ReportFileMapper;
import org.jboot.kernel.report.service.IReportFileService;
import org.springframework.stereotype.Service;

/**
 * UReport Service
 *
 * @author Corsak
 */
@Service
public class ReportFileServiceImpl extends ServiceImpl<ReportFileMapper, ReportFileEntity> implements IReportFileService {
}
