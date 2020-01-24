package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.it.gruppo2.operationsDB.*;
import com.it.gruppo2.brewDay2.*;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class BrewDayMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void invokeGUI(Connection connection, int id_birraio) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrewDayMenu window = new BrewDayMenu(connection, birraio);
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
	public BrewDayMenu(Connection connection, Birraio birraio) {
		initialize( connection,  birraio);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Connection connection, Birraio birraio) {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Statement stmt;
		stmt = connection.createStatement();
		
		//crazione oggetti birra basati sul database
		String sql = "SELECT id_birra FROM birra WHERE id_birraio = '" + birraio.getId_birraio()+ "'";
		ResultSet rs = stmt.executeQuery(sql);
		
		sql = "SELECT (COUNT id_birra) AS numBirre FROM birra WHERE id_birraio = '" + birraio.getId_birraio()+ "'";
		ResultSet rs1 = stmt.executeQuery(sql);
		rs1.next();
		Birra[] birra = new Birra[rs1.getInt("numBirre")];
		
		for(int i=0; rs.next(); i++) {
			birra[i]=new Birra(rs.getInt("id_birra"), rs.getString("nome"), rs.getString("tipo"));
		}
		
		/*sql = "SELECT id_birra FROM ricetta WHERE id_birra = '" + birra.getId_birra()+ "'";
		rs = stmt.executeQuery(sql);
		
		sql = "SELECT (COUNT id_birra) AS numRicette FROM ricetta WHERE id_birra = '" + birra.getId_birra()+ "'";
		rs1 = stmt.executeQuery(sql);
		rs1.next();
		
		Ricetta[] ricetta = new Ricetta[rs1.getInt("numBirre")];
		
		for(int i=0; rs.next(); i++) {
			ricetta[i]=new Ricetta(rs.getInt("id_"), rs.getString("nome"), rs.getString("tipo"));
		}*/
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnRicetta = new JMenu("Ricette");
		menuBar.add(mnRicetta);
		
		JMenuItem mntmNuovaRicetta = new JMenuItem("Nuova ricetta");
		mnRicetta.add(mntmNuovaRicetta);
		
		JMenuItem mntmCaricaRicetta = new JMenuItem("Carica ricetta");
		mntmCaricaRicetta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statement stmt;
				try {
					stmt = connection.createStatement();
					System.out.println("Checking existing brewer...");
					String sql = "SELECT * FROM birraio WHERE username = " + userField.getText() + " AND password = " + passwordField.getText();
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()) {
						JDialog d = new JDialog(frame, "Hello "+ rs.getString("nome"), true);
					    d.setLocationRelativeTo(frame);
					    d.setVisible(true);
					}
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnRicetta.add(mntmCaricaRicetta);
		
		JMenu mnIngredienti = new JMenu("Ingredienti");
		menuBar.add(mnIngredienti);
		
		JMenuItem mntmVisIngrDisp = new JMenuItem("Visualizza ingredienti disponibili");
		mnIngredienti.add(mntmVisIngrDisp);
		
		JMenu mnProfilo = new JMenu("Profilo");
		menuBar.add(mnProfilo);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Log out");
		mnProfilo.add(mntmNewMenuItem);
	}
}
