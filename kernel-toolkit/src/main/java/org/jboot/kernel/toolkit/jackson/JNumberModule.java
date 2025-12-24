package org.jboot.kernel.toolkit.jackson;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigDecimal;
import java.math.BigInteger;
public class JNumberModule extends SimpleModule {
	public static final JNumberModule INSTANCE = new JNumberModule();
	public JNumberModule() {
		super(JNumberModule.class.getName());
		// Long 和 BigInteger 采用定制的逻辑序列化，避免超过js的精度
		this.addSerializer(Long.class, BigNumberSerializer.instance);
		this.addSerializer(Long.TYPE, BigNumberSerializer.instance);
		this.addSerializer(BigInteger.class, BigNumberSerializer.instance);
		// BigDecimal 采用 toString 避免精度丢失，前端采用 decimal.js 来计算。
		this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
	}
}
