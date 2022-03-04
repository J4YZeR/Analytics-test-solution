package ru.myapps.analytics.helper.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class CustomException extends RuntimeException {
	private CustomExceptionType exceptionType;

	public CustomException(CustomExceptionType exceptionType, Object... messageArguments) {
		super(MessageFormat.format(exceptionType.getMessage(), messageArguments));
		this.exceptionType = exceptionType;
	}

	public HttpStatus getStatus() {
		return exceptionType.getStatus();
	}
}
