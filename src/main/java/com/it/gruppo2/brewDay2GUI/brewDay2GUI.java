package com.it.gruppo2.brewDay2GUI;

import java.awt.EventQueue;
import java.sql.Connection;
import com.it.gruppo2.operationsDB.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class brewDay2GUI {

	private JFrame frmLogin;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Launch the application.
	 */
	public int invokeGUI(Connection connection) {
		this.setConnection(connection);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					brewDay2GUI window = new brewDay2GUI();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return 1;
	}

	/**
	 * Create the application.
	 */
	public brewDay2GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 450, 300);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(29, 41, 103, 20);
		frmLogin.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setText("ex: mangoverde");
		txtUsername.setBounds(29, 77, 146, 26);
		frmLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(208, 41, 69, 20);
		frmLogin.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setText("Insert here...");
		txtPassword.setBounds(208, 77, 146, 26);
		frmLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnLogin.setBounds(208, 156, 146, 29);
		frmLogin.getContentPane().add(btnLogin);
		
		JButton btnRegistration = new JButton("Registration");
		btnRegistration.setBounds(29, 156, 146, 29);
		frmLogin.getContentPane().add(btnRegistration);
	}
}
