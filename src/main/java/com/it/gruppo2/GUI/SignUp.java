package com.it.gruppo2.GUI;

import com.it.gruppo2.brewDay2.*;
import java.awt.EventQueue;   

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class SignUp {

	
	private JFrame frame;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp(connection);
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
	public SignUp(Connection connection) {
		initialize(connection);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Connection connection) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 369);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JFormattedTextField fieldNome = new JFormattedTextField();
		fieldNome.setBounds(238, 40, 86, 20);
		frame.getContentPane().add(fieldNome);
		
		final JFormattedTextField fieldCognome = new JFormattedTextField();
		fieldCognome.setBounds(238, 71, 86, 20);
		frame.getContentPane().add(fieldCognome);
		
		final JFormattedTextField fieldUsername = new JFormattedTextField();
		fieldUsername.setBounds(238, 164, 86, 20);
		frame.getContentPane().add(fieldUsername);
		
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
		lblConfPassword.setBounds(73, 137, 160, 14);
		frame.getContentPane().add(lblConfPassword);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = fieldNome.getText();
				String cognome = fieldCognome.getText();
				String username = fieldUsername.getText();
				char[] pass = passwordField.getPassword();
				char[] passCon = passwordField_1.getPassword();
				String password = new String(pass);
				String confpass = new String(passCon);
				
				//controllo sui campi se sono stati compilati
				if(nome.length()==0 ||cognome.length()==0 || username.length()==0 || password.length()==0 || confpass.length()==0)	
					JOptionPane.showMessageDialog(null, "Inserisci tutti i dati");
				else {
					//controllo sulla conferma della password
					if(!password.equals(confpass)) 					//FUNZIONA MA NON SO PERCHE DA RIVEDERE
						JOptionPane.showMessageDialog(null, "Le password non coincidono");
					else {
						try (Statement stmt = connection.createStatement()){
							System.out.println("Checking existing brewer...");
							String sql = "SELECT id_birraio FROM birraio WHERE username = '" + username+ "'";
							try (ResultSet rs = stmt.executeQuery(sql)){
								if(rs.next()) {
									JDialog d = new JDialog(frame, username + " già registrato!", true);
								    d.setLocationRelativeTo(frame);
								    d.setVisible(true);
								}else {
									System.out.println("Insert new brewer into db...");
									sql = "INSERT INTO birraio (nome, cognome, username, password)" +
							                   "VALUES ('"+nome+"','"+cognome+"','"+username+"','"+password+"')";
									stmt.executeUpdate(sql);
									sql = "SELECT id_birraio FROM birraio WHERE username = '" + username+ "'";
									try (ResultSet rs1 = stmt.executeQuery(sql);){
										rs1.next();
										Birraio brewerBirraio = new Birraio(rs1.getInt("id_birraio"), nome, cognome, username, password);
										BrewDayMenu grapInterf = new BrewDayMenu(connection, brewerBirraio);
										grapInterf.invokeGUI(connection, brewerBirraio);
										frame.dispose();
									} catch (Exception e2) {
										// TODO: handle exception
									}
								}
							} catch (Exception e2) {
								// TODO: handle exception
							}
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					}
				}
				
			}
		});
		btnSignUp.setBounds(156, 274, 89, 23);
		frame.getContentPane().add(btnSignUp);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(238, 102, 86, 20);
		frame.getContentPane().add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(238, 134, 86, 20);
		frame.getContentPane().add(passwordField_1);
	}
}
