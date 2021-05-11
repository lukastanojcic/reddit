package com.example.reddit.exception;

public class SpringRedditException extends RuntimeException {
	
	public SpringRedditException(String exceptionMessage, Exception exception) {
		super(exceptionMessage, exception);
	}
	
	public SpringRedditException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
