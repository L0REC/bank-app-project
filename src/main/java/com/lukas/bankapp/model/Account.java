package com.lukas.bankapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class Account {

	private Long id;
	
	@NotNull(message = "Balance cannot be null")
	@DecimalMin(value = "0.0", message = "Balance cannot be negative")
	private Double balance;
	
	private List<Transaction> transactions;
	private LocalDateTime createdAt;

	public Account()  {
		this.balance = 0.0;
		this.transactions = new ArrayList<>();
		this.createdAt = LocalDateTime.now();
	}
	
	public Account(Double initialBalance) {
		this();
		if(initialBalance < 0) {
			throw new IllegalArgumentException("Initial balance cannot be negative");
		}
		this.balance = initialBalance;
		this.transactions.add(new Transaction("INITIAL_DEPOSIT", initialBalance, this.balance));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
		
	}
	
	
	
	
}
