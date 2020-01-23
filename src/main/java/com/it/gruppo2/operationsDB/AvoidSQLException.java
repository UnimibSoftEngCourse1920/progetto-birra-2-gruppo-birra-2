package com.it.gruppo2.operationsDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class AvoidSQLException {
   public static void main(String[]args){
      Connection con = null;
      try {
         con = DriverManager.
         getConnection("jdbc:mysql://localhost:3306/sample?useSSL=false", "root", "");
         System.out.println("Connection is successful !!!!!");
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}
