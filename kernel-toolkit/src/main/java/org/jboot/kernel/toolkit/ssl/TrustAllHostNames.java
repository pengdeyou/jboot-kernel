package org.jboot.kernel.toolkit.ssl;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
public class TrustAllHostNames implements HostnameVerifier {
	public static final TrustAllHostNames INSTANCE = new TrustAllHostNames();
	@Override
	public boolean verify(String s, SSLSession sslSession) {
		return true;
	}
}
