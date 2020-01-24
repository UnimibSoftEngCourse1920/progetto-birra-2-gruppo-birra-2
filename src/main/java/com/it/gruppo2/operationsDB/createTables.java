package com.it.gruppo2.operationsDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// creating a tables
public class createTables {
	public void setTables(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		System.out.println("Creating table in given database...");
		String sql = "CREATE TABLE birra " + "(id INTEGER not NULL, " + " first VARCHAR(255), " + " last VARCHAR(255), "
				+ " age INTEGER, " + " PRIMARY KEY ( id ))";
		stmt.executeUpdate(sql);
		System.out.println("Created table in given database...");
	}
}
