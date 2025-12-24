package org.jboot.kernel.redis.serializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BytesWrapper<T> implements Cloneable {
	private T value;
	@Override
	@SuppressWarnings("unchecked")
	public BytesWrapper<T> clone() {
		try {
			return (BytesWrapper) super.clone();
		} catch (CloneNotSupportedException e) {
			return new BytesWrapper<>();
		}
	}
}
