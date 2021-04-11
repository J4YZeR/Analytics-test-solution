package ru.myapps.analytics.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.myapps.analytics.exception.CustomException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<Object> handleInfluxDbException(
			CustomException ex) {
		return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
	}
}
