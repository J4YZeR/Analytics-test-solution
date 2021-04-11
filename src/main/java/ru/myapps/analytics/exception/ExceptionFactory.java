package ru.myapps.analytics.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class ExceptionFactory {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionFactory.class);

	public static CustomException create(final CustomExceptionType exceptionType, final Object... messageArguments) {
		logger.error(MessageFormat.format(exceptionType.getMessage(), messageArguments));
		return new CustomException(exceptionType, messageArguments);
	}
}
