package ru.myapps.analytics.helper.exception;

import org.springframework.http.HttpStatus;

public enum CustomExceptionType {
	ID_NOT_FOUND(HttpStatus.NOT_FOUND, "Cannot find template with id {0}"),
	VARIABLE_NOT_MATCHES_TYPE(HttpStatus.BAD_REQUEST, "Variable {0} not matches type {1}"),
	CANNOT_SEND_MESSAGE_TO_RECIPIENT(HttpStatus.INTERNAL_SERVER_ERROR,
			"Cannot send message to following recipient: {0}"),
	CANNOT_SEND_MESSAGE_TO_RECIPIENTS(HttpStatus.INTERNAL_SERVER_ERROR,
			"Cannot send message to following recipients: {0}");

	private final HttpStatus status;
	private final String message;

	CustomExceptionType(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
