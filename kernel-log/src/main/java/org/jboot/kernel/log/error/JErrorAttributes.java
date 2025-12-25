package org.jboot.kernel.log.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.log.props.JLogProperties;
import org.jboot.kernel.log.publisher.ErrorLogPublisher;
import org.jboot.kernel.toolkit.api.R;
import org.jboot.kernel.toolkit.api.ResultCode;
import org.jboot.kernel.toolkit.utils.BeanUtil;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 全局异常处理
 *
 * @author Chill
 */
@Slf4j
@RequiredArgsConstructor
public class JErrorAttributes extends DefaultErrorAttributes {
	private final JLogProperties bladeLogProperties;

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		String requestUri = this.getAttr(webRequest, "jakarta.servlet.error.request_uri");
		Integer status = this.getAttr(webRequest, "jakarta.servlet.error.status_code");
		Throwable error = getError(webRequest);
		R result;
		if (error == null) {
			log.error("URL:{} error status:{}", requestUri, status);
			result = R.fail(ResultCode.FAILURE, "系统未知异常[HttpStatus]:" + status);
		} else {
			log.error(String.format("URL:%s error status:%d", requestUri, status), error);
			result = R.fail(status, error.getMessage());
		}
		//发送服务异常事件
		if (bladeLogProperties.getError()) {
			ErrorLogPublisher.publishEvent(error, requestUri);
		}
		return BeanUtil.toMap(result);
	}

	@Nullable
	private <T> T getAttr(WebRequest webRequest, String name) {
		return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
