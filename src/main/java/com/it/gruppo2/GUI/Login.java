package com.it.gruppo2.GUI;

import java.awt.EventQueue;   
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.it.gruppo2.brewDay2.Birraio;

public class Login {

	private JFrame frame;
	private JButton registerButton;
	private JButton loginButton;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**l
	 * Launch the application.
	 */
	public int invokeGUI(final Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login(connection);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return 0;
	}
	
	

	/**
	 * Create the application.
	 */
	public Login(Connection connection) {
		initialize(connection);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Connection connection) {
		frame =  new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Label per inserimento user
		lblNewLabel = new JLabel("User");
		lblNewLabel.setBounds(79, 74, 94, 14);
		frame.getContentPane().add(lblNewLabel);
		
		//Text field per inserimento user
		final JFormattedTextField userField = new JFormattedTextField();
		userField.setText("a.russo");
		userField.setToolTipText("Inserisci username");
		userField.setBounds(183, 69, 100, 25);
		frame.getContentPane().add(userField);
		
		//Label password
		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(79, 105, 94, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		final JFormattedTextField passwordField = new JFormattedTextField();
		passwordField.setText("password");
		passwordField.setBounds(183, 99, 100, 26);
		frame.getContentPane().add(passwordField);
		
		//Button per il login
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent loginButton) {
				Statement stmt;
				try {
					stmt = connection.createStatement();
					System.out.println("Checking existing brewer...");
					String sql = "SELECT * FROM birraio WHERE username = '" + userField.getText() + "' AND password = '" + passwordField.getText() +"'";
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()) {
						Birraio brewerBirraio = new Birraio(rs.getInt("id_birraio"), rs.getString("nome"), rs.getString("cognome"), rs.getString("username"), rs.getString("password"));
						BrewDayMenu grapInterf = new BrewDayMenu(connection, brewerBirraio);
						grapInterf.invokeGUI(connection, brewerBirraio);
						frame.dispose();
					}
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		loginButton.setBounds(183, 131, 100, 25);
		frame.getContentPane().add(loginButton);
		
		//button per la sign up
		registerButton = new JButton("Sign up");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp grapInterf = new SignUp(connection);
				grapInterf.invokeGUI(connection);
				frame.dispose();
			}
		});
		registerButton.setBounds(183, 203, 100, 25);
		frame.getContentPane().add(registerButton);
		
		//Label varie
		JLabel lblWelcome = new JLabel("BREW DAY 2 All GRAIN - Welcome!");
		lblWelcome.setBounds(79, 43, 261, 14);
		frame.getContentPane().add(lblWelcome);
		
		
	}
}
