package org.jboot.kernel.datascope.exception;
public class DataScopeException extends RuntimeException {
	public DataScopeException() {
	}
	public DataScopeException(String message) {
		super(message);
	}
	public DataScopeException(Throwable cause) {
		super(cause);
	}
}
