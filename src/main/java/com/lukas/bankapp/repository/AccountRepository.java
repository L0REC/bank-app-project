package com.lukas.bankapp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

	private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private Connection connection;

	public void initialize() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, "sa", "");

		Statement stmt = connection.createStatement();
		stmt.execute("CREATE TABLE accounts (id VARCHAR(50),balance DECIMAL(10,2))");
		stmt.execute("INSERT INTO accounts VALUES ('account', 100.0)");
		stmt.close();
	}

	public Double getAccountBalance() throws SQLException {
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE id = 'account'")) {
			return rs.next() ? rs.getDouble("balance") : 0.0;
		}

	}

	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public Connection getConnection() {
		return connection;
	}
}
