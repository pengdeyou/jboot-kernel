package org.jboot.kernel.oss;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jboot.kernel.oss.model.JFile;
import org.jboot.kernel.oss.model.OssFile;
import org.jboot.kernel.oss.props.OssProperties;
import org.jboot.kernel.oss.rule.OssRule;
import org.jboot.kernel.toolkit.utils.StringPool;
import org.jboot.kernel.toolkit.utils.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;
@AllArgsConstructor
public class TencentCosTemplate implements OssTemplate {
	/**
	 * 腾讯云 COS 客户端
	 */
	private final COSClient cosClient;
	/**
	 * OSS配置属性
	 */
	private final OssProperties ossProperties;
	/**
	 * OSS规则对象
	 */
	private final OssRule ossRule;
	@Override
	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			cosClient.createBucket(getBucketName(bucketName));
			// TODO: 权限是否需要修改为私有，当前为 公有读、私有写
			cosClient.setBucketAcl(getBucketName(bucketName), CannedAccessControlList.PublicRead);
		}
	}
	@Override
	@SneakyThrows
	public void removeBucket(String bucketName) {
		cosClient.deleteBucket(getBucketName(bucketName));
	}
	@Override
	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return cosClient.doesBucketExist(getBucketName(bucketName));
	}
	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		cosClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}
	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		cosClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}
	@Override
	@SneakyThrows
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}
	@Override
	@SneakyThrows
	public OssFile statFile(String bucketName, String fileName) {
		ObjectMetadata stat = cosClient.getObjectMetadata(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(fileName);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.getContentMD5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setPutTime(stat.getLastModified());
		ossFile.setContentType(stat.getContentType());
		return ossFile;
	}
	@Override
	@SneakyThrows
	public String filePath(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}
	@Override
	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}
	@Override
	@SneakyThrows
	public String fileLink(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}
	@Override
	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}
	/**
	 * 文件对象
	 *
	 * @param file 上传文件类
	 * @return JFile
	 */
	@Override
	@SneakyThrows
	public JFile putFile(MultipartFile file) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}
	/**
	 * @param fileName 上传文件名
	 * @param file     上传文件类
	 * @return JFile
	 */
	@Override
	@SneakyThrows
	public JFile putFile(String fileName, MultipartFile file) {
		return putFile(ossProperties.getBucketName(), fileName, file);
	}
	@Override
	@SneakyThrows
	public JFile putFile(String bucketName, String fileName, MultipartFile file) {
		return putFile(bucketName, fileName, file.getInputStream());
	}
	@Override
	@SneakyThrows
	public JFile putFile(String fileName, InputStream stream) {
		return putFile(ossProperties.getBucketName(), fileName, stream);
	}
	@Override
	@SneakyThrows
	public JFile putFile(String bucketName, String fileName, InputStream stream) {
		return put(bucketName, stream, fileName, false);
	}
	/**
	 * 上传文件到腾讯云COS
	 *
	 * @param bucketName 存储桶名称
	 * @param stream     输入流
	 * @param key       文件名
	 * @param cover     是否覆盖上传
	 * @return JFile 上传文件信息
	 */
	@SneakyThrows
	public JFile put(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		String originalName = key;
		key = getFileName(key);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		// 覆盖上传
		if (cover) {
			cosClient.putObject(getBucketName(bucketName), key, stream, objectMetadata);
		} else {
			PutObjectResult response = cosClient.putObject(getBucketName(bucketName), key, stream, objectMetadata);
			int retry = 0;
			int retryCount = 5;
			while (!StringUtils.hasText(response.getETag()) && retry < retryCount) {
				response = cosClient.putObject(getBucketName(bucketName), key, stream, objectMetadata);
				retry++;
			}
		}
		JFile file = new JFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setDomain(getOssHost(bucketName));
		file.setLink(fileLink(bucketName, key));
		return file;
	}
	@Override
	@SneakyThrows
	public void removeFile(String fileName) {
		cosClient.deleteObject(getBucketName(), fileName);
	}
	@Override
	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		cosClient.deleteObject(getBucketName(bucketName), fileName);
	}
	@Override
	@SneakyThrows
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}
	@Override
	@SneakyThrows
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}
	/**
	 * 获取默认存储桶名称
	 *
	 * @return String 存储桶名称
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}
	/**
	 * 根据规则生成存储桶名称
	 *
	 * @param bucketName 存储桶名称
	 * @return String 处理后的存储桶名称
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName).concat(StringPool.DASH).concat(ossProperties.getAppId());
	}
	/**
	 * 根据规则生成文件名称
	 *
	 * @param originalFilename 原始文件名
	 * @return String 处理后的文件名
	 */
	private String getFileName(String originalFilename) {
		return ossRule.fileName(originalFilename);
	}
	/**
	 * 获取指定存储桶的访问域名
	 *
	 * @param bucketName 存储桶名称
	 * @return String 访问域名
	 */
	public String getOssHost(String bucketName) {
		String prefix = getEndpoint().contains("https://") ? "https://" : "http://";
		return prefix + cosClient.getClientConfig().getEndpointBuilder().buildGeneralApiEndpoint(getBucketName(bucketName));
	}
	/**
	 * 获取默认存储桶的访问域名
	 *
	 * @return String 访问域名
	 */
	public String getOssHost() {
		return getOssHost(ossProperties.getBucketName());
	}
	/**
	 * 获取腾讯云COS服务的Endpoint
	 *
	 * @return String 腾讯云COS Endpoint
	 */
	public String getEndpoint() {
		if (StringUtil.isBlank(ossProperties.getTransformEndpoint())) {
			return ossProperties.getEndpoint();
		}
		return StringUtil.removeSuffix(ossProperties.getTransformEndpoint(), StringPool.SLASH);
	}
}
