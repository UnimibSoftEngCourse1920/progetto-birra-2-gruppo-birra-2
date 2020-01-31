package com.it.gruppo2.brewDay2;

import java.io.IOException; 
import java.sql.Connection;

import com.it.gruppo2.operationsDB.*;

import com.it.gruppo2.GUI.*;

/**
 * BrewDayApp
 *
**/

public class App {
	public static void main(String[] args) throws IOException {
		//create phase
		connectionDB connectionDB = new connectionDB();
		connectionDB.setDBCredential();
		connectionDB.createServerConnection();
		//provare creare il DB
		connectionDB.createDB();
		//connessione al DB
		Connection connection = connectionDB.connectDB();
		//lancio dell'App
		if(connection != null) {
			Login login = new Login(connection);
			login.invokeGUI(connection);
		}
		
	}
}
