package org.jboot.kernel.redis.config;
import org.jboot.kernel.redis.serializer.ProtoStuffSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.RedisSerializer;
@AutoConfiguration(before = RedisTemplateConfiguration.class)
@ConditionalOnClass(name = "io.protostuff.Schema")
public class ProtoStuffSerializerConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public RedisSerializer<Object> redisSerializer() {
		return new ProtoStuffSerializer();
	}
}
