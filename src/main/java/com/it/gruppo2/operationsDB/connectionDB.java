package com.it.gruppo2.operationsDB;

import java.io.IOException;
import java.sql.*;

public class connectionDB {
	public String dbpathString, usernameString, pwdString, nameDBString;
	
	public String getNameDBString() {
		return nameDBString;
	}

	public void setNameDBString(String nameDBString) {
		this.nameDBString = nameDBString;
	}

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
				setNameDBString(createDB.setDB(connection));
				}
		} catch (Exception e) {
			System.err.println("Excpetion: " + e.getMessage());
		} finally {
			if (connection != null)
				closingConnection(connection);
		}
	}
	
	public void closingConnection(Connection con) {
		try {
			con.close();
			if(con.isClosed())
				System.out.println("Successfully closed the connection");
		} catch (SQLException e) {
			System.err.println("Excpetion: " + e.getMessage());
		}
	}
	
	public void createDBConnection() {
		Connection connection = null;
		try {
			if(getNameDBString() != null) //case where database already exists
			{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(getDbpathString()+getNameDBString(), getUsernameString(), getPwdString()); 
			if (!connection.isClosed()) {
				System.out.println("Successfully connected to database..."); 
				createTables createTables= new createTables(); 
				createTables.setTables(connection); 
				}
			}
		} catch (Exception e) {
			System.err.println("Excpetion: " + e.getMessage());
		} finally {
			if (connection != null)
				closingConnection(connection);
		}
	}
	
	public Connection connectionToDB() {
		Connection connection = null;
		if(getNameDBString() == null)
			setNameDBString("brewdaydb");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(getDbpathString()+getNameDBString(), getUsernameString(), getPwdString()); 
			if (!connection.isClosed()) {
				System.out.println("Successfully connected to database...");
				}
		} catch (Exception e) {
			System.err.println("Excpetion: " + e.getMessage());
		}
		return connection;
	}
}
