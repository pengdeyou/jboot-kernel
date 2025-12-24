package org.jboot.kernel.crypto.core;
import lombok.RequiredArgsConstructor;
import org.jboot.kernel.crypto.annotation.decrypt.ApiDecrypt;
import org.jboot.kernel.crypto.bean.CryptoInfoBean;
import org.jboot.kernel.crypto.config.ApiCryptoProperties;
import org.jboot.kernel.crypto.util.ApiCryptoUtil;
import org.jboot.kernel.toolkit.jackson.JsonUtil;
import org.jboot.kernel.toolkit.utils.Charsets;
import org.jboot.kernel.toolkit.utils.StringUtil;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.lang.reflect.Parameter;
@RequiredArgsConstructor
public class ApiDecryptParamResolver implements HandlerMethodArgumentResolver {
	private final ApiCryptoProperties properties;
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return AnnotatedElementUtils.hasAnnotation(parameter.getParameter(), ApiDecrypt.class);
	}
	@Nullable
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Parameter parameter = methodParameter.getParameter();
		ApiDecrypt apiDecrypt = AnnotatedElementUtils.getMergedAnnotation(parameter, ApiDecrypt.class);
		String text = webRequest.getParameter(properties.getParamName());
		if (StringUtil.isBlank(text)) {
			return null;
		}
		CryptoInfoBean infoBean = new CryptoInfoBean(apiDecrypt.value(), apiDecrypt.secretKey());
		byte[] textBytes = text.getBytes(Charsets.UTF_8);
		byte[] decryptData = ApiCryptoUtil.decryptData(properties, textBytes, infoBean);
		return JsonUtil.readValue(decryptData, parameter.getType());
	}
}
