package com.it.gruppo2.operationsDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// creating a DB
public class createDB { 
	public String setDB(Connection connection) throws SQLException {
		String nameDBString = "brewdayDB";//db to be logic
		Statement stmt = connection.createStatement();
		System.out.println("Creating database...");
		String sql = "CREATE DATABASE " + nameDBString;
		stmt.executeUpdate(sql);
		System.out.println("Database created successfully..."); 
		return nameDBString;
	}
}
