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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BrewDayMenu {

	JFrame frame;
	private ArrayList<Ricetta> ricettArrayList = new ArrayList<Ricetta>();
	private ArrayList<Attrezzatura> attrezzaturaArrayList = new ArrayList<Attrezzatura>();
	private ArrayList<Birra> birraList = new ArrayList<Birra>();
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
		
		//crazione oggetti birra basati sul database

		String sql = "SELECT COUNT(id_birra) AS numBirre FROM birra WHERE id_birraio = '" + brewerBirraio.getId_birraio() + "'";
		ResultSet rs = stmt.executeQuery(sql);
		int max=0;
		if(rs.next()) {
			max=rs.getInt("numBirre");
			
		}
		rs.close();
		//seleziono tutte le birre del birraio
		sql = "SELECT * FROM birra WHERE id_birraio = '" + brewerBirraio.getId_birraio()+ "'";
		rs = stmt.executeQuery(sql);
		DefaultListModel<String> nomeList = new DefaultListModel<String>();
		DefaultListModel<String> tipoList = new DefaultListModel<String>();
		if(rs.next())
		{
			for(int i=0; i<max; i++, rs.next()) {
				//inserisco tutte le birre in una lista
				birraList.add(i,new Birra(rs.getInt("id_birra"), rs.getString("nome"), rs.getString("tipo"), rs.getInt("id_birraio")));
				nomeList.addElement(birraList.get(i).getNome());
				tipoList.addElement(birraList.get(i).getTipo());
			}
		}else {
			nomeList.addElement("Non esiste alcuna birra!");
			tipoList.addElement("");
		}
		final JList<String> listd = new JList<String>(nomeList);
		listd.setBounds(64, 32, 200, 200);
		frame.getContentPane().add(listd);
		
		final JList<String> listr = new JList<String>(tipoList);
		listr.setBounds(265, 32, 200, 200);
		frame.getContentPane().add(listr);
		
		rs.close();
		
		listd.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
              int index = listd.getSelectedIndex();
              RicetteBirra ricetteBirra = new RicetteBirra(connection, birraList.get(index), brewerBirraio);
              ricetteBirra.invokeGUI(connection, birraList.get(index), brewerBirraio);
              frame.dispose();
          }
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnRicetta = new JMenu("Ricette");
		menuBar.add(mnRicetta);
		
		JMenuItem mntmNuovaRicetta = new JMenuItem("Nuova ricetta");
		mntmNuovaRicetta.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperations newRic = new CRUDoperations(connection, brewerBirraio, "newRic");
				newRic.invokeGUI(connection, brewerBirraio, "newRic");
				frame.dispose();
			}
		});
		mnRicetta.add(mntmNuovaRicetta);
		
		JMenuItem mntmEliminaRicetta = new JMenuItem("Elimina ricetta");
		mnRicetta.add(mntmEliminaRicetta);
		
		JMenuItem mntmVisualizzaRicetta = new JMenuItem("Visualizza ricetta");
		mnRicetta.add(mntmVisualizzaRicetta);
		
		JMenu mnIngredienti = new JMenu("Ingredienti");
		menuBar.add(mnIngredienti);
		
		JMenuItem mntmVisIngrDisp = new JMenuItem("Visualizza ingredienti");
		mntmVisIngrDisp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperations showIngr = new CRUDoperations(connection, brewerBirraio, "showIngr");
				showIngr.invokeGUI(connection, brewerBirraio, "showIngr");
				frame.dispose();
			}
		});
		mnIngredienti.add(mntmVisIngrDisp);
		
		JMenuItem mntmAggiungiIngrediente = new JMenuItem("Aggiungi ingrediente");
		mntmAggiungiIngrediente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperations showIngr = new CRUDoperations(connection, brewerBirraio, "addIngr");
				showIngr.invokeGUI(connection, brewerBirraio, "addIngr");
				frame.dispose();
			}
		});
		mnIngredienti.add(mntmAggiungiIngrediente);
		
		JMenu mnProfilo = new JMenu("Profilo");
		menuBar.add(mnProfilo);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Login login = new Login(connection);
				login.invokeGUI(connection);
				frame.dispose();
			}
		});
		mnProfilo.add(mntmLogOut);
		
		JMenu mnWSIBT = new JMenu("WSIBT");
		menuBar.add(mnWSIBT);
		
		JMenuItem mntmConsigliami = new JMenuItem("Consigliami");
		mnWSIBT.add(mntmConsigliami);
		mntmConsigliami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Statement stmtStatement, stmt1;
					stmtStatement = connection.createStatement();
					String sql = "SELECT * FROM attrezzatura WHERE id_birraio = '"+brewerBirraio.getId_birraio()+"'";
					ResultSet rSet = stmtStatement.executeQuery(sql);
					while(rSet.next()) {
						attrezzaturaArrayList.add(new Attrezzatura(rSet.getInt("id_attrezzatura"), rSet.getString("nome"), rSet.getInt("capacita"), rSet.getInt("id_birraio")));
					}
					rSet.close();
					//prendo tutte le ricette di quel birraio
		    		sql = "SELECT DISTINCT ricetta.id_ricetta, ricetta.nome FROM ricetta INNER JOIN birra ON ricetta.id_birra = birra.id_birra INNER JOIN birraio ON birraio.id_birraio = birra.id_birraio WHERE birraio.id_birraio = '"+brewerBirraio.getId_birraio()+"'";
		    		//inserisco il risultato
		    		stmt1 = connection.createStatement();
		    		rSet = stmt1.executeQuery(sql);
		    		Statement stmt2;
	    			stmt2 = connection.createStatement();
		    		while(rSet.next())
		    		{
						//prendo tutti gli ingredienti di quella ricetta
						sql = "SELECT * FROM ingrediente INNER JOIN ricetta ON ingrediente.id_ingrediente = ricetta.id_ingrediente WHERE ricetta.id_ricetta = '"+ rSet.getInt("id_ricetta") +"'";
			    		ResultSet rs3 = stmt2.executeQuery(sql);
			    		int j = 0;
			    		while(rs3.next())
			    		{	
			    			//inserisco nella lista delle ricette la ricetta in posizione i
							ricettArrayList.add(j,new Ricetta(rSet.getInt("id_ricetta"),rs3.getDouble("quantita"),rs3.getInt("id_birra"),rs3.getInt("id_ingrediente"), rSet.getString("nome")));
							j++;
			    		}
			    		rs3.close();
		    		}
		    		rSet.close();
					
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
	
}