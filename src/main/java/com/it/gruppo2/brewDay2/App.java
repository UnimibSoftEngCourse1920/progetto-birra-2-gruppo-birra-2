package com.it.gruppo2.brewDay2;

import java.io.IOException;
import java.sql.Connection;

import com.it.gruppo2.operationsDB.*;

import com.it.gruppo2.brewDay2GUI.brewDay2GUI;

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
		connectionDB.createDBConnection();

		//query phase
		Connection connection = connectionDB.connectionToDB();
		
		brewDay2GUI grapInterf = new brewDay2GUI();
		grapInterf.invokeGUI(connection);
	}
	
	
}
