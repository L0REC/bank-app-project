package com.lukas.bankapp.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lukas.bankapp.exception.InsufficientFundsException;
import com.lukas.bankapp.model.Account;
import com.lukas.bankapp.model.Transaction;
import com.lukas.bankapp.repository.AccountRepository;

@Service
public class AccountService {

	private Account account;
	private AccountRepository accountRepository;

	public AccountService() {
		try {
			accountRepository = new AccountRepository();
			accountRepository.initialize();
			
			Double balance = accountRepository.getAccountBalance();
			this.account = new Account(balance);
			
		} catch(SQLException e) {
			throw new RuntimeException("Failed to initialize database", e);
		}
	}

	public Account getAccount() {
		return account;
	}

	public Double getBalance() {
		return account.getBalance();
	}

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	public Account deposit(Double amount) {
		logger.info("Processing deposit of ${}", amount);

		if (amount <= 0) {
			logger.warn("Invalid deposit amount: {}", amount);
			throw new IllegalArgumentException("Deposit amount must be positive");
		}

		Double oldBalance = account.getBalance();
		Double newBalance = oldBalance + amount;
		account.setBalance(newBalance);

		logger.info("Deposit successful. Balance changed from ${} to ${}", oldBalance, newBalance);

		Transaction transaction = new Transaction("DEPOSIT", amount, newBalance);
		account.addTransaction(transaction);

		return account;
	}

	public Account withdraw(Double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Withdrawal amount must be positive");
		}

		if (amount > account.getBalance()) {
			throw new InsufficientFundsException(String.format(
					"Insufficient funds. Current balance: %.2f, Requested: %.2f", account.getBalance(), amount));

		}

		Double oldBalance = account.getBalance();
		Double newBalance = oldBalance - amount;
		account.setBalance(newBalance);

		Transaction transaction = new Transaction("WITHDRAWAL", amount, newBalance);
		account.addTransaction(transaction);

		return account;
	}

	public Account resetAccount() {
		this.account = new Account(100.0);
		return account;
	}
}
