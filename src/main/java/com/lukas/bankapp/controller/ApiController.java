package com.lukas.bankapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lukas.bankapp.exception.InsufficientFundsException;
import com.lukas.bankapp.model.Account;
import com.lukas.bankapp.service.AccountService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/account")
	public ResponseEntity<Account> getAccount() {
		Account account = accountService.getAccount();
		return ResponseEntity.ok(account);
	}

	@GetMapping("/balance")
	public ResponseEntity<Map<String, Object>> getBalance() {
		Double balance = accountService.getBalance();
		Map<String, Object> response = new HashMap<>();
		response.put("balance", balance);
		response.put("message", "Balance retrieved successfully");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/deposit")
	public ResponseEntity<Map<String, Object>> deposit(@RequestBody Map<String, Double> request) {
		Map<String, Object> response = new HashMap<>();

		try {
			Double amount = request.get("amount");
			Account account = accountService.deposit(amount);

			response.put("success", true);
			response.put("message", String.format("Deposit of %.2f successful", amount));
			response.put("balance", account.getBalance());
			response.put("account", account);

			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}

	@PostMapping("/withdraw")
	public ResponseEntity<Map<String, Object>> withdraw(@RequestBody Map<String, Double> request) {
		Map<String, Object> response = new HashMap<>();

		try {
			Double amount = request.get("amount");
			Account account = accountService.withdraw(amount);

			response.put("success", true);
			response.put("message", String.format("Withdrawal of %.2f successful", amount));
			response.put("balance", account.getBalance());
			response.put("account", account);
			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException | InsufficientFundsException e) {
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping("/reset")
	public ResponseEntity<Map<String, Object>> resetAccount() {
		Account account = accountService.resetAccount();
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Account reset successfully");
		response.put("account", account);
		return ResponseEntity.ok(response);
	}

}
