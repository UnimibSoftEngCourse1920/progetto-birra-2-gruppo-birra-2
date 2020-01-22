package com.it.gruppo2.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.it.gruppo2.operationsDB.connectionDB;

public class BrewDayGUI {

	private JFrame frame;
	private JPasswordField passwordField;
	private JButton registerButton;
	private JButton loginButton;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**l
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrewDayGUI window = new BrewDayGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the application.
	 */
	public BrewDayGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame =  new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Label per inserimento user
		lblNewLabel = new JLabel("User");
		lblNewLabel.setBounds(127, 74, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		//Text field per inserimento user
		final JFormattedTextField userField = new JFormattedTextField();
		userField.setToolTipText("Inserisci username");
		userField.setBounds(183, 69, 100, 25);
		frame.getContentPane().add(userField);
		
		//Label password
		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(127, 105, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		
		
		
		//Password Field
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Inserisci password");
		passwordField.setBounds(183, 100, 100, 25);
		frame.getContentPane().add(passwordField);
		
		//Button per il login
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent loginButton) {
				
				JOptionPane.showMessageDialog(frame, "Ok boomer");
				//Inizio connessione
				Connection connection = connectionDB.connectionToDB();
				
				Statement stmt;
				try {
					stmt = connection.createStatement();
					String sql = "select id_birraio from birraio where user is " + userField.getText() + " and password is " + passwordField.getPassword();
					stmt.executeUpdate(sql); 
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("Errore nella connessione");
				}
				
				
				
				//Fine connessione
				connectionDB.closingConnection(connection);
			}
		});
		 
		loginButton.setBounds(171, 131, 100, 25);
		frame.getContentPane().add(loginButton);
		
		//button per la sign up
		registerButton = new JButton("Sign up");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Prova");
			}
		});
		registerButton.setBounds(171, 206, 100, 25);
		frame.getContentPane().add(registerButton);
		
		//Label varie
		JLabel lblWelcome = new JLabel("BREW DAY 2 - Welcome!");
		lblWelcome.setBounds(160, 44, 123, 14);
		frame.getContentPane().add(lblWelcome);
		
		
	}
}
