package org.jboot.kernel.cloud.header;
import org.jboot.kernel.cloud.props.JFeignHeadersProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import java.util.concurrent.Callable;
public class JHttpHeadersCallable<V> implements Callable<V> {
	private final Callable<V> delegate;
	@Nullable
	private HttpHeaders httpHeaders;
	public JHttpHeadersCallable(Callable<V> delegate,
									@Nullable JFeignAccountGetter accountGetter,
									JFeignHeadersProperties properties) {
		this.delegate = delegate;
		this.httpHeaders = JHttpHeadersContextHolder.toHeaders(accountGetter, properties);
	}
	@Override
	public V call() throws Exception {
		if (httpHeaders == null) {
			return delegate.call();
		}
		try {
			JHttpHeadersContextHolder.set(httpHeaders);
			return delegate.call();
		} finally {
			JHttpHeadersContextHolder.remove();
			httpHeaders.clear();
			httpHeaders = null;
		}
	}
}
