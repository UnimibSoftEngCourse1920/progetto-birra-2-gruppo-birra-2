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
		//create phse
		connectionDB connectionDB = new connectionDB();
		connectionDB.setDBCredential();
		connectionDB.createServerConnection();
		Connection connection = connectionDB.createDBConnection();
		//GUI starts
		Login grapInterf = new Login(connection);
		grapInterf.invokeGUI(connection);
	}
	
	
}
