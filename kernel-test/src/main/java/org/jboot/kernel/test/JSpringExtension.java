package org.jboot.kernel.test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.jboot.kernel.launch.JApplication;
import org.jboot.kernel.launch.constant.AppConstant;
import org.jboot.kernel.launch.constant.NacosConstant;
import org.jboot.kernel.launch.constant.SentinelConstant;
import org.jboot.kernel.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;
public class JSpringExtension extends SpringExtension {
	@Override
	public void beforeAll(@NonNull ExtensionContext context) throws Exception {
		super.beforeAll(context);
		setUpTestClass(context);
	}
	private void setUpTestClass(ExtensionContext context) {
		Class<?> clazz = context.getRequiredTestClass();
		JBootTest jBootTest = AnnotationUtils.getAnnotation(clazz, JBootTest.class);
		if (jBootTest == null) {
			throw new JBootTestException(String.format("%s must be @JBootTest .", clazz));
		}
		String appName = jBootTest.appName();
		String profile = jBootTest.profile();
		boolean isLocalDev = JApplication.isLocalDev();
		Properties props = System.getProperties();
		props.setProperty("jboot.kernel.env", profile);
		props.setProperty("jboot.kernel.name", appName);
		props.setProperty("jboot.kernel.is-local", String.valueOf(isLocalDev));
		props.setProperty("jboot.kernel.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
		props.setProperty("jboot.kernel.service.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("spring.application.name", appName);
		props.setProperty("spring.profiles.active", profile);
		props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("info.desc", appName);
		props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.prefix", NacosConstant.NACOS_CONFIG_PREFIX);
		props.setProperty("spring.cloud.nacos.config.file-extension", NacosConstant.NACOS_CONFIG_FORMAT);
		props.setProperty("spring.cloud.sentinel.transport.dashboard", SentinelConstant.SENTINEL_ADDR);
		props.setProperty("spring.main.allow-bean-definition-overriding", "true");
		// 加载自定义组件
		if (jBootTest.enableLoader()) {
			List<LauncherService> launcherList = new ArrayList<>();
			SpringApplicationBuilder builder = new SpringApplicationBuilder(clazz);
			ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
			launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).toList()
				.forEach(launcherService -> launcherService.launcher(builder, appName, profile));
		}
		System.err.printf("---[junit.test]:[%s]---启动中，读取到的环境变量:[%s]%n", appName, profile);
	}
}
