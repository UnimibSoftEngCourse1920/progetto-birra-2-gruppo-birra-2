package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.it.gruppo2.brewDay2.*;

import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		Statement stmt;
		stmt = connection.createStatement();
		
		//crazione oggetti birra bsati sul database
		
		String sql = "SELECT COUNT(id_birra) AS numBirre FROM birra WHERE id_birraio = '" + brewerBirraio.getId_birraio() + "'";
		ResultSet rs = stmt.executeQuery(sql);
		final ArrayList<Birra> birra = new ArrayList<Birra>();
		int max=0;
		if(rs.next()) {
			max=rs.getInt("numBirre");
			
		}
		rs.close();
			
		sql = "SELECT * FROM birra WHERE id_birraio = '" + brewerBirraio.getId_birraio()+ "'";
		rs = stmt.executeQuery(sql);
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		if(rs.next())
		{
			for(int i=0; i<max; i++, rs.next()) {
				birra.add(i,new Birra(rs.getInt("id_birra"), rs.getString("nome"), rs.getString("tipo"), rs.getInt("id_birraio")));
				demoList.addElement(birra.get(i).getNome());
			}
		}else {
			demoList.addElement("Non esiste alcuna birra!");
		}
		final JList<String> listd = new JList<String>(demoList);
		listd.setBounds(64, 32, 200, 200);
		frame.getContentPane().add(listd);
		rs.close();
		
		listd.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int index = listd.getSelectedIndex();
                
                Statement stmt;
				try {
					stmt = connection.createStatement();
	                String sql = "SELECT COUNT(id_birra) AS numRicette FROM ricetta WHERE id_birra = '" + (int)birra.get(index).getId_birra() + "'";
	        		ResultSet rs;
					rs = stmt.executeQuery(sql);
	        		final ArrayList<Ricetta> ricettArrayList = new ArrayList<Ricetta>();
	        		int max=0;
	        		if(rs.next()) {
	        			max=rs.getInt("numRicette");
	        		}
	        		rs.close();
	        		sql = "SELECT * FROM ricetta WHERE id_birra = '" + (int)birra.get(index).getId_birra()+ "'";
	        		rs = stmt.executeQuery(sql);
	        		DefaultListModel<String> ricetteListModel = new DefaultListModel<String>();
	        		if(rs.next())
	        		{
	        			for(int i=0; i<max; i++, rs.next()) {
	        				ricettArrayList.add(i,new Ricetta(rs.getDouble("quantita"),rs.getInt("id_birra"), rs.getInt("id_ingrediente")));
	        				ricetteListModel.addElement(ricettArrayList.get(i).getQuantita().toString());
	        			}
	        		}else {
	        			ricetteListModel.addElement("Non esiste alcuna ricetta!");
	        		}
	        		JList<String> listr = new JList<String>(ricetteListModel);
	        		listr.setBounds(305, 32, 200, 200);
	        		frame.getContentPane().add(listr);
	        		rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
		
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
