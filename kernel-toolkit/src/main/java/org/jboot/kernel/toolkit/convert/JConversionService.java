package org.jboot.kernel.toolkit.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author Corsak
 */
public class JConversionService extends ApplicationConversionService {
	@Nullable
	private static volatile JConversionService SHARED_INSTANCE;

	public JConversionService() {
		this(null);
	}

	public JConversionService(@Nullable StringValueResolver embeddedValueResolver) {
		super(embeddedValueResolver);
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link JConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 * @return the shared {@code JConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		JConversionService sharedInstance = JConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (JConversionService.class) {
				sharedInstance = JConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new JConversionService();
					JConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
