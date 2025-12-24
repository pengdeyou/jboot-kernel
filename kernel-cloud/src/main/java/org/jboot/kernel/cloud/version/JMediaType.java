package org.jboot.kernel.cloud.version;
import lombok.Getter;
import org.springframework.http.MediaType;
@Getter
public class JMediaType {
	private static final String MEDIA_TYPE_TEMP = "application/vnd.%s.%s+json";
	private final String appName = "jboot";
	private final String version;
	private final MediaType mediaType;
	public JMediaType(String version) {
		this.version = version;
		this.mediaType = MediaType.valueOf(String.format(MEDIA_TYPE_TEMP, appName, version));
	}
	@Override
	public String toString() {
		return mediaType.toString();
	}
}
