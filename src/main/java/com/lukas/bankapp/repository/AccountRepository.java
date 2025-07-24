package com.lukas.bankapp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

	private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private Connection connection;

	public void initialize() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, "sa", "");

		try (PreparedStatement createStmt = connection.prepareStatement(
				"CREATE TABLE accounts (id VARCHAR(50), balance DECIMAL(10,2))")) {
			createStmt.execute();
		}

		try (PreparedStatement insertStmt = connection.prepareStatement(
				"INSERT INTO accounts VALUES (?,?)")) {
			
			String[] usernames = {"user1","user2","user3"};
			double[] balances = {100.0, 150.0, 200.0};
			
			//全てアカウントをbatchに追加
			for(int i = 0; i < usernames.length; i++) {
				insertStmt.setString(1, usernames[i]);
				insertStmt.setDouble(2, balances[i]);
				insertStmt.addBatch();
			}
			insertStmt.executeBatch();
		}
	}

	public Double getAccountBalance() throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement("SELECT balance FROM accounts WHERE id = ?")) {
			stmt.setString(1, "user1");
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() ? rs.getDouble("balance") : 0.0;
			}
		}
	}

	public void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
