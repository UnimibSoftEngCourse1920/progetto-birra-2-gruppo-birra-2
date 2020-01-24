package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.it.gruppo2.brewDay2.*;

import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class BrewDayMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection, final Birraio brewerBirraio) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrewDayMenu window = new BrewDayMenu(connection, brewerBirraio);
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
	public BrewDayMenu(Connection connection, Birraio brewerBirraio) {
		try {
			initialize(connection, brewerBirraio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize(final Connection connection, Birraio brewerBirraio) throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		Statement stmt;
		stmt = connection.createStatement();
		
		//crazione oggetti birra bsati sul database
		
		String sql = "SELECT COUNT(id_birra) AS numBirre FROM birra WHERE id_birraio = '" + brewerBirraio.getId_birraio() + "'";
		ResultSet rs = stmt.executeQuery(sql);
		Birra[] birra = null;
		int max=0;
		if(rs.next()) {
			max=rs.getInt("numBirre");
			birra = new Birra[max];	
			
		}
		rs.close();
		
		DefaultListModel demoList = new DefaultListModel();
		 
			
		sql = "SELECT * FROM birra WHERE id_birraio = '" + brewerBirraio.getId_birraio()+ "'";
		rs = stmt.executeQuery(sql);
		if(rs.next())
		{
			for(int i=0; i<max; i++, rs.next()) {
				birra[i]=new Birra(rs.getInt("id_birra"), rs.getString("nome"), rs.getString("tipo"), rs.getInt("id_birraio"));
				System.out.println(birra[i].getNome());
				demoList.addElement(birra[i].getNome());
			}
			JList listd = new JList(demoList);
			 listd.setBounds(64, 32, 200, 200);
				frame.getContentPane().add(listd);
		}
		rs.close();
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
