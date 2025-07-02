package com.lukas.bankapp.service;

import org.springframework.stereotype.Service;

import com.lukas.bankapp.exception.InsufficientFundsException;
import com.lukas.bankapp.model.Account;
import com.lukas.bankapp.model.Transaction;



@Service
public class AccountService {

	private Account account;

	public AccountService() {
		this.account = new Account(100.0);
	}

	public Account getAccount() {
		return account;
	}

	public Double getBalance() {
		return account.getBalance();
	}

	public Account deposit(Double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Deposit amount must be positive");
		}

		Double oldBalance = account.getBalance();
		Double newBalance = oldBalance + amount;
		account.setBalance(newBalance);

		Transaction transaction = new Transaction("DEPOSIT", amount, newBalance);
		account.addTransaction(transaction);

		return account;
	}

	public Account withdraw(Double amount) {
		if (amount <= 0) {
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
