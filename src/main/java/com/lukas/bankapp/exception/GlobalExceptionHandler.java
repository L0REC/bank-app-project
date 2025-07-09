package com.lukas.bankapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<Map<String, Object>> handleInsufficientFunds(InsufficientFundsException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("message", e.getMessage());
		response.put("error", "INSUFFICIENT_FUNDS");
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("message", e.getMessage());
		response.put("error", "INTERNAL_ERROR");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
