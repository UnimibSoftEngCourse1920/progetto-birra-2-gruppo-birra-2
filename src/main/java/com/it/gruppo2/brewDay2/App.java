package com.it.gruppo2.brewDay2;

import java.io.IOException;

import com.it.gruppo2.connectionDB.connectionDB;

/**
 * BrewDayApp
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	connectionDB connectionDB = new connectionDB();
    	connectionDB.setDBCredential();
    	connectionDB.createDBConnection();
    }
}
