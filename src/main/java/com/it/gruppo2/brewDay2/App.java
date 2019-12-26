package com.it.gruppo2.brewDay2;

import java.sql.*;

/**
 * Connection to BrewDayDB
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Connection connection = null;
    		try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String dbpathString = "jdbc:mysql://localhost:3306/brewdaydb";
				String usernameString = "root";
				String pwdString = "";
				connection = DriverManager.getConnection(dbpathString, usernameString, pwdString);
				if(!connection.isClosed())
					System.out.println("Successfully connected to brewDayDB...");
    		} catch (Exception e) {
				System.err.println("Excpetion: "+ e.getMessage());
			}
    		finally {
				try {
					if(connection != null)
						connection.close();
				} catch (SQLException e) {
					System.err.println("Excpetion: "+ e.getMessage());
				}
			}
    }
}
