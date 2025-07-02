package com.lukas.bankapp.model;

import java.time.LocalDateTime;

public class Transaction {

	private String type;
	private Double amount;
	private Double balanceAfter;
	private LocalDateTime timestamp;
	
	public Transaction() {}
	
	public Transaction(String type, Double amount, Double balanceAfter) {
		super();
		this.type = type;
		this.amount = amount;
		this.balanceAfter = balanceAfter;
		this.timestamp = LocalDateTime.now();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(Double balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
