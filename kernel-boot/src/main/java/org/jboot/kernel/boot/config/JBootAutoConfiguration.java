
package org.jboot.kernel.boot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboot.kernel.launch.props.JProperties;
import org.jboot.kernel.tool.constant.SystemConstant;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类
 *
 * @author Corsak
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({
	JProperties.class
})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AllArgsConstructor
public class JBootAutoConfiguration {

	private JProperties jProperties;

	/**
	 * 全局变量定义
	 *
	 * @return SystemConstant
	 */
	@Bean
	public SystemConstant fileConst() {
		SystemConstant me = SystemConstant.me();

		//设定开发模式
		me.setDevMode(("dev".equals(jProperties.getEnv())));

		//设定文件上传远程地址
		me.setDomain(jProperties.get("upload-domain", "http://localhost:8888"));

		//设定文件上传是否为远程模式
		me.setRemoteMode(jProperties.getBoolean("remote-mode", true));

		//远程上传地址
		me.setRemotePath(jProperties.get("remote-path", System.getProperty("user.dir") + "/work/j"));

		//设定文件上传头文件夹
		me.setUploadPath(jProperties.get("upload-path", "/upload"));

		//设定文件下载头文件夹
		me.setDownloadPath(jProperties.get("download-path", "/download"));

		//设定上传图片是否压缩
		me.setCompress(jProperties.getBoolean("compress", false));

		//设定上传图片压缩比例
		me.setCompressScale(jProperties.getDouble("compress-scale", 2.00));

		//设定上传图片缩放选择:true放大;false缩小
		me.setCompressFlag(jProperties.getBoolean("compress-flag", false));

		return me;
	}

}
