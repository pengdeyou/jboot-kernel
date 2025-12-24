package org.jboot.kernel.cloud.props;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;
import java.util.Arrays;
import java.util.List;
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("jboot.kernel.feign.headers")
public class JFeignHeadersProperties {
	/**
	 * 用于 聚合层 向调用层传递用户信息 的请求头，默认：x-j-account
	 */
	private String account = "X-J-Account";
	/**
	 * RestTemplate 和 Fegin 透传到下层的 Headers 名称表达式
	 */
	@Nullable
	private String pattern = "J*";
	/**
	 * RestTemplate 和 Fegin 透传到下层的 Headers 名称列表
	 */
	private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "authorization", "token-auth", "Authorization");
}
