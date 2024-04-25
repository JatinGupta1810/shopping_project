package com.shopping.project.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
		Map<String, Object> map = new HashMap<>();
		map.put("ERROR", ex.getMessage());
		map.put("STATUS CODE", HttpStatus.UNAUTHORIZED.value());
		map.put("STATUS ", HttpStatus.UNAUTHORIZED.name());
		return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
	}

}
