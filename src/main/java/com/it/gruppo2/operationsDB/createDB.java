package com.it.gruppo2.operationsDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// creating a DB
public class createDB { 
	private String nameDBString;
	
	public String getNameDBString() {
		return nameDBString;
	}
	public void setNameDBString() {
		this.nameDBString = "brewdaydb";
	}
	
	public String setDB(Connection connection) throws SQLException {
		setNameDBString();
		try (Statement stmt = connection.createStatement();){
			System.out.println("Creating database or trying to reach...");
			String sql = "CREATE DATABASE " + getNameDBString();
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully..."); 
			return getNameDBString();
		}
	}
}
