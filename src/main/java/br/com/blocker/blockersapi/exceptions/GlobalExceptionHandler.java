package br.com.blocker.blockersapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<String> handleBindException(
			WebExchangeBindException e) {
		String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return new ResponseEntity<>(defaultMessage, HttpStatus.BAD_REQUEST);
	}
}