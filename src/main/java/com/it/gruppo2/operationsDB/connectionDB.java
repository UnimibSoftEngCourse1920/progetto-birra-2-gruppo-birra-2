package com.it.gruppo2.operationsDB;

import java.io.IOException;
import java.sql.*;

public class connectionDB {
	public String dbpathString, usernameString, pwdString, nameDBString;

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

	// receive from console the information to create the conn at anyone DB
	public void setDBCredential() throws IOException {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//		System.out.println("Inserire dbpathString:");	
//		setDbpathString("jdbc:mysql://"+reader.readLine());
//		System.out.println("Inserire usernameString:");
//		setUsernameString(reader.readLine());
//		System.out.println("Inserire pwdString:");
//		setPwdString(reader.readLine());

//		Mock to deleted and uncommented previous lines
		setDbpathString("jdbc:mysql://localhost:3306/");//delete autobrewday
		setUsernameString("root");
		setPwdString("");
	}

	public void createServerConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			connection = DriverManager.getConnection(getDbpathString(), getUsernameString(), getPwdString()); 
			if (!connection.isClosed()) {
				System.out.println("Successfully connected to server..."); 
				createDB createDB= new createDB(); 
				nameDBString=createDB.setDB(connection); }
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
	public void createDBConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			connection = DriverManager.getConnection(getDbpathString()+nameDBString, getUsernameString(), getPwdString()); 
			if (!connection.isClosed()) {
				System.out.println("Successfully connected to database..."); 
				createTables createTables= new createTables(); 
				createTables.setTables(connection); 
				}
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
