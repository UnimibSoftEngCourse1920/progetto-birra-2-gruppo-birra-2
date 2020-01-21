package com.it.gruppo2.brewDay2GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.it.gruppo2.operationsDB.connectionDB;
import javax.swing.JToggleButton;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class BrewDayGUI {

	private JFrame frame;
	private JPasswordField passwordField;
	private final Action action = new SwingAction();

	/**
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JFormattedTextField userField = new JFormattedTextField();	//campo username
		frame.getContentPane().add(userField, BorderLayout.NORTH);
		
		
		
		passwordField = new JPasswordField(); 		//campo password
		passwordField.setEchoChar('•');
		frame.getContentPane().add(passwordField, BorderLayout.CENTER);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		loginButton.setAction(action);
		frame.getContentPane().add(loginButton, BorderLayout.SOUTH);
		

	}

