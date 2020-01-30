package com.it.gruppo2.operationsDB;

import java.io.IOException;
import java.sql.*;

public class connectionDB {
	public String dbpathString;
	public String usernameString;
	public String pwdString;
	public String nameDBString;
	
	public String getNameDBString() {
		return nameDBString;
	}

	public void setNameDBString(String thisNameDBString) {
		nameDBString = thisNameDBString;
	}

	public  String getPwdString() {
		return pwdString;
	}

	public void setPwdString(String pwdString) {
		this.pwdString = pwdString;
	}

	public  String getUsernameString() {
		return usernameString;
	}

	public void setUsernameString(String usernameString) {
		this.usernameString = usernameString;
	}

	public  String getDbpathString() {
		return dbpathString;
	}

	public void setDbpathString(String dbpathString) {
		this.dbpathString = dbpathString;
	}

	// receive from console the information to create the conn at anyone DB
	public void setDBCredential() throws IOException {

//		Mock to deleted and uncommented previous lines
		setDbpathString("jdbc:mysql://localhost:3306/");//delete brewdaydb
		setUsernameString("root");
		setPwdString("");
	}

	public void createServerConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			connection = DriverManager.getConnection(getDbpathString(), getUsernameString(), getPwdString()); 
			//caso in cui il server esiste
			if (!connection.isClosed()) {
				System.out.println("Successfully connected to server..."); 
				//provvedo a creare il DB
				createDB createDB= new createDB(); 
				//se non ricevo nulla allora il DB esiste altrimenti setto il nome del DB 
				setNameDBString(createDB.setDB(connection));
				}
		} catch (Exception e) {
			System.err.println("Excpetion: " + e.getMessage());
		} finally {
			if (connection != null)
				closingConnection(connection);
		}
	}
	
	public  void closingConnection(Connection con) {
		try {
			con.close();
			if(con.isClosed())
				System.out.println("Successfully closed the connection");
		} catch (SQLException e) {
			System.err.println("Excpetion: " + e.getMessage());
		}
	}
	
	public Connection createDBConnection() {
		Connection connection = null;
		try {
			if(getNameDBString() != null) //case where database not exists
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(getDbpathString()+getNameDBString(), getUsernameString(), getPwdString());
				if (!connection.isClosed()) {
					System.out.println("Successfully connected to database..."); 
					createTables createTables= new createTables(); 
					createTables.setTables(connection); 
					return connection;
					}
			}
			else //case exists db
			{
				setNameDBString("brewdaydb");
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(getDbpathString()+getNameDBString(), getUsernameString(), getPwdString()); 
				if (!connection.isClosed()) {
					System.out.println("Successfully connected to database...");
					return connection;
				}	
			}
		} catch (Exception e) {
			System.err.println("Excpetion: " + e.getMessage());
		}
		return connection;
	}
}
