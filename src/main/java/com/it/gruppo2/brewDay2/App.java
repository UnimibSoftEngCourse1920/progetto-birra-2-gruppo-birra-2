package com.it.gruppo2.brewDay2;

import java.io.IOException;

import com.it.gruppo2.operationsDB.*;

/**
 * BrewDayApp
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		connectionDB connectionDB = new connectionDB();
		connectionDB.setDBCredential();
		connectionDB.createServerConnection();
		connectionDB.createDBConnection();
		System.out.println( "Prova ribo" );
	}
}
