package org.jboot.kernel.toolkit.geo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum GeoType {
	/**
	 * WGS84
	 */
	WGS84("WGS84", "地球坐标系，国际通用坐标系") {
		@Override
		public GeoPoint toWGS84(double lon, double lat) {
			return new GeoPoint(lon, lat);
		}
		@Override
		public GeoPoint toGCJ02(double lon, double lat) {
			return GeoUtil.wgs84ToGcj02(lon, lat);
		}
		@Override
		public GeoPoint toBD09(double lon, double lat) {
			return GeoUtil.wgs84ToBd09(lon, lat);
		}
	},
	GCJ02("GCJ02", "火星坐标系，高德、腾讯、阿里等使用") {
		@Override
		public GeoPoint toWGS84(double lon, double lat) {
			return GeoUtil.gcj02ToWgs84(lon, lat);
		}
		@Override
		public GeoPoint toGCJ02(double lon, double lat) {
			return new GeoPoint(lon, lat);
		}
		@Override
		public GeoPoint toBD09(double lon, double lat) {
			return GeoUtil.gcj02ToBd09(lon, lat);
		}
	},
	BD09("BD09", "百度坐标系，百度、搜狗等使用") {
		@Override
		public GeoPoint toWGS84(double lon, double lat) {
			return GeoUtil.bd09toWgs84(lon, lat);
		}
		@Override
		public GeoPoint toGCJ02(double lon, double lat) {
			return GeoUtil.bd09ToGcj02(lon, lat);
		}
		@Override
		public GeoPoint toBD09(double lon, double lat) {
			return new GeoPoint(lon, lat);
		}
	};
	@JsonValue
	private final String type;
	private final String desc;
	/**
	 * 转换成 地球坐标系
	 *
	 * @param lon lon
	 * @param lat lat
	 * @return GeoPoint
	 */
	public abstract GeoPoint toWGS84(double lon, double lat);
	/**
	 * 转换成 火星坐标系
	 *
	 * @param lon lon
	 * @param lat lat
	 * @return GeoPoint
	 */
	public abstract GeoPoint toGCJ02(double lon, double lat);
	/**
	 * 转换成 百度坐标系
	 *
	 * @param lon lon
	 * @param lat lat
	 * @return GeoPoint
	 */
	public abstract GeoPoint toBD09(double lon, double lat);
	/**
	 * 获取坐标系
	 *
	 * @param type type 坐标系类型
	 * @return GeoType
	 */
	@JsonCreator
	public static GeoType getGeoType(String type) {
		for (GeoType geoType : values()) {
			if (geoType.type.equalsIgnoreCase(type)) {
				return geoType;
			}
		}
		throw new IllegalArgumentException("未知的坐标系类型" + type);
	}
}
