package com.it.gruppo2.connectionDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class connectionDB {
	String dbpathString, usernameString, pwdString;

	public String getPwdString() {
		return pwdString;
	}

	public void setPwdString(String pwdString) {
		this.pwdString = pwdString;
	}

	public String getUsernameString() {
		return usernameString;
	}

	public void setUsernameString(String usernameString) {
		this.usernameString = usernameString;
	}

	public String getDbpathString() {
		return dbpathString;
	}

	public void setDbpathString(String dbpathString) {
		this.dbpathString = dbpathString;
	}
	//receive from console the information to create the conn at anyone DB 
	public void setDBCredential() throws IOException {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//		System.out.println("Inserire dbpathString:");	
//		setDbpathString(reader.readLine());
//		System.out.println("Inserire usernameString:");
//		setUsernameString(reader.readLine());
//		System.out.println("Inserire pwdString:");
//		setPwdString(reader.readLine());

//		Mock to delated and decommented previus lines
		setDbpathString("jdbc:mysql://localhost:3306/brewdaydb");
		setUsernameString("root");
		setPwdString("");
	}
	
	public void createDBConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();//creating an instance of the framework that allows to communicate with DB
//			String dbpathString = "jdbc:mysql://localhost:3306/brewdaydb";
//			String usernameString = "root";
//			String pwdString = "";
			connection = DriverManager.getConnection(getDbpathString(), getUsernameString(), getPwdString());
			if (!connection.isClosed())
				System.out.println("Successfully connected to brewDayDB...");
		} catch (Exception e) {
			System.err.println("Excpetion: " + e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println("Excpetion: " + e.getMessage());
			}
		}
	}
}
