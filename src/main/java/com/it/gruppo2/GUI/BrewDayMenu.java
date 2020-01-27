package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ListModel;

import com.it.gruppo2.brewDay2.*;

import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class BrewDayMenu {

	JFrame frame;
	private ArrayList<Ricetta> ricettArrayList = new ArrayList<Ricetta>();
	private ArrayList<Attrezzatura> attrezzaturaArrayList = new ArrayList<Attrezzatura>();
	private ArrayList<Birra> birra = new ArrayList<Birra>();
	private final Action action = new SwingAction();
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
	private void initialize(final Connection connection, final Birraio brewerBirraio) throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		Statement stmt;
		stmt = connection.createStatement();
		
		//crazione oggetti birra bsati sul database
		
		String sql = "SELECT COUNT(id_birra) AS numBirre FROM birra WHERE id_birraio = '" + brewerBirraio.getId_birraio() + "'";
		ResultSet rs = stmt.executeQuery(sql);
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
		final JList<String> listBirre = new JList<String>(demoList);
		listBirre.setBounds(64, 32, 200, 200);
		frame.getContentPane().add(listBirre);
		
		final JList<String> listAttrezzature = new JList<String>(demoList);
		listAttrezzature.setBounds(332, 32, 200, 200);
		frame.getContentPane().add(listAttrezzature);
		
		rs.close();
		
		listBirre.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int index = listBirre.getSelectedIndex();
                RicetteBirra ricetteBirra = new RicetteBirra();
                ricetteBirra.invokeGUI(connection, index, brewerBirraio);
                Statement stmt;
				try {
					stmt = connection.createStatement();
	                String sql = "SELECT COUNT(id_birra) AS numRicette FROM ricetta WHERE id_birra = '" + (int)birra.get(index).getId_birra() + "'";
	        		ResultSet rs;
					rs = stmt.executeQuery(sql);
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
	        				ricettArrayList.add(i,new Ricetta(rs.getInt("id_ricetta"),rs.getDouble("quantita"),rs.getInt("id_birra"), rs.getInt("id_ingrediente")));
	        				ricetteListModel.addElement(ricettArrayList.get(i).getId_ricetta().toString());
	        			}
	        		}else {
	        			ricetteListModel.addElement("Non esiste alcuna ricetta!");
	        		}
	        		rs.close();
	        		
	        		DefaultListModel<String> attrezzaturaListModel = new DefaultListModel<String>();
					
	        		
	                sql = "SELECT COUNT(id_attrezzatura) AS numAtt FROM attrezzatura WHERE id_birraio = '" + (int)birra.get(index).getId_birraio() + "'";
					rs = stmt.executeQuery(sql);
	        		max=0;
	        		if(rs.next()) {
	        			max=rs.getInt("numAtt");
	        		}
	        		rs.close();
	        		sql = "SELECT * FROM attrezzatura WHERE id_birraio = '" + (int)birra.get(index).getId_birraio() + "'";
	        		rs = stmt.executeQuery(sql);
	        		
	        		if(rs.next())
	        		{
	        			for(int i=0; i<max; i++, rs.next()) {
	        				
	        				
	        				attrezzaturaArrayList.add(i,new Attrezzatura(rs.getInt("id_attrezzatura"),rs.getString("nome"),rs.getInt("capacita"), rs.getInt("id_birraio")));
	        				attrezzaturaListModel.addElement(attrezzaturaArrayList.get(i).getId_attrezzatura().toString());
	        			}
	        		}else {
	        			attrezzaturaListModel.addElement("Non esiste alcuna ricetta!");
	        		}
	        		
	        		
	        		
	        		rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				

            }
        });
		
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
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mnProfilo.add(mntmLogOut);
		
		JMenu mnWSIBT = new JMenu("WSIBT");
		menuBar.add(mnWSIBT);
		
		JMenuItem mntmConsigliami = new JMenuItem("Consigliami");
		mnWSIBT.add(mntmConsigliami);
		mntmConsigliami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					WSIBT grapInterf = new WSIBT(connection, brewerBirraio, ricettArrayList, attrezzaturaArrayList);
					grapInterf.invokeGUI(connection, brewerBirraio, ricettArrayList, attrezzaturaArrayList);
					frame.dispose();
				} catch (SQLException e1) {
					//
					e1.printStackTrace();
				}
				
			}
		});
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
