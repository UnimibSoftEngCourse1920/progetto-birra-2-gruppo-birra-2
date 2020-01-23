package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.it.gruppo2.operationsDB.connectionDB;

import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class SignUp {

	private JFrame frame;
	private JPasswordField fieldPass;
	private JPasswordField fieldConfPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp();
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
	public SignUp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JFormattedTextField fieldNome = new JFormattedTextField();
		fieldNome.setBounds(207, 40, 86, 20);
		frame.getContentPane().add(fieldNome);
		
		final JFormattedTextField fieldCognome = new JFormattedTextField();
		fieldCognome.setBounds(207, 71, 86, 20);
		frame.getContentPane().add(fieldCognome);
		
		final JFormattedTextField fieldUsername = new JFormattedTextField();
		fieldUsername.setBounds(207, 164, 86, 20);
		frame.getContentPane().add(fieldUsername);
		
		fieldPass = new JPasswordField();
		fieldPass.setBounds(207, 102, 86, 20);
		frame.getContentPane().add(fieldPass);
		
		fieldConfPass = new JPasswordField();
		fieldConfPass.setBounds(207, 133, 86, 20);
		frame.getContentPane().add(fieldConfPass);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setBounds(73, 167, 126, 14);
		frame.getContentPane().add(lblUsername);
		
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setBounds(71, 43, 126, 14);
		frame.getContentPane().add(lblNome);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setHorizontalAlignment(SwingConstants.LEFT);
		lblCognome.setBounds(71, 74, 126, 14);
		frame.getContentPane().add(lblCognome);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(73, 105, 126, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblConfPassword = new JLabel("Conferma password");
		lblConfPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblConfPassword.setBounds(71, 136, 126, 14);
		frame.getContentPane().add(lblConfPassword);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nome = fieldNome.getText();
				String cognome = fieldCognome.getText();
				String username = fieldUsername.getText();
				char[] password = fieldPass.getPassword();
				char[] confpass = fieldConfPass.getPassword();
				
				//controllo sui campi se sono stati compilati
				if(nome.length()==0 ||cognome.length()==0 || username.length()==0 || password.length==0 || confpass.length==0)	
					JOptionPane.showMessageDialog(null, "Inserisci tutti i dati");
				else {
					//controllo sulla conferma della password
					if(password != confpass) 					//FUNZIONA MA NON SO PERCHE DA RIVEDERE
						JOptionPane.showMessageDialog(null, "Le password non coincidono");
					else {
						//connessione a datrabase e query
						Connection connection = connectionDB.connectionToDB();
						
						Statement stmt;
						try {
							stmt = connection.createStatement();
							String sql = "insert into birraio (Nome, cognome, username, password) values ("+nome+", "+cognome+", \"+username+\", \"+password+\")";
							stmt.executeUpdate(sql); 
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.println("Errore nella connessione");
						}
						
						
						
						//Fine connessione
						connectionDB.closingConnection(connection);
						
					}
				}
				
			}
		});
		btnSignUp.setBounds(157, 296, 89, 23);
		frame.getContentPane().add(btnSignUp);
		
	
	}
}
