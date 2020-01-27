package com.it.gruppo2.GUI;

import java.awt.EventQueue; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import com.it.gruppo2.brewDay2.Birra;
import com.it.gruppo2.brewDay2.Ingrediente;
import com.it.gruppo2.brewDay2.Ricetta;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class RicetteBirra {

	private JFrame frame;
	private ArrayList<Ricetta> ricettArrayList = new ArrayList<Ricetta>();
	private ArrayList<Ingrediente> ingredienteArrayList = new ArrayList<Ingrediente>();
	
	
	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection,final Birra birra) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RicetteBirra window = new RicetteBirra(connection, birra);
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
	public RicetteBirra(Connection connection, Birra birra) {
		initialize(connection, birra);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Connection connection,final Birra birra) {
		frame = new JFrame();
		frame.setBounds(100, 100, 773, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Statement  stmt2, stmt4;
		try {
			stmt2 = connection.createStatement();
			stmt4 = connection.createStatement();
    		ResultSet rs1,rs3;
    		DefaultListModel<String> ricetteListModel = new DefaultListModel<String>();
    		DefaultListModel<String> ingredienteListModel = new DefaultListModel<String>();
    		DefaultListModel<String> weightListModel = new DefaultListModel<String>();
            
    		//prendo tutte le ricette di quella birra
    		String sql = "SELECT DISTINCT id_ricetta, nome FROM ricetta WHERE id_birra = '" + (int)birra.getId_birra()+ "'";
    		//inserisco il risultato
    		rs1 = stmt2.executeQuery(sql);
    		while(rs1.next())
    		{
				//prendo tutti gli ingredienti di quella ricetta
				sql = "SELECT * FROM ingrediente INNER JOIN ricetta ON ingrediente.id_ingrediente = ricetta.id_ingrediente WHERE ricetta.id_birra = '" + (int)birra.getId_birra()+ "' AND ricetta.id_ricetta = '"+ rs1.getInt("id_ricetta") +"'";
	    		rs3 = stmt4.executeQuery(sql);
	    		int j = 0;
	    		while(rs3.next())
	    		{	
	    			//inserisco nella lista delle ricette la ricetta in posizione i
					ricettArrayList.add(j,new Ricetta(rs1.getInt("id_ricetta"),rs3.getDouble("quantita"),(int)birra.getId_birra(),rs3.getInt("id_ingrediente"), rs1.getString("nome")));
					
    				ingredienteArrayList.add(j,new Ingrediente(rs3.getInt("id_ingrediente"),rs3.getString("nome"),rs3.getString("tipo")));
    				ingredienteListModel.addElement(ingredienteArrayList.get(j).getNome());
    				
    				weightListModel.addElement(ricettArrayList.get(j).getQuantita().toString());
    				
    				j++;
	    		}
	    		ricetteListModel.addElement(rs1.getString("nome"));
	    		ingredienteListModel.addElement("fine ingredienti ricetta "+ rs1.getString("nome"));
	    		weightListModel.addElement("fine pesi ingredienti della ricetta "+ rs1.getString("nome"));
	    		rs3.close();
    		}
    		rs1.close();
    		frame.getContentPane().setLayout(null);
    		JList<String> listRicette = new JList<String>(ricetteListModel);
    		listRicette.setValueIsAdjusting(true);
    		listRicette.setBounds(32, 100, 200, 200);
    		frame.getContentPane().add(listRicette);
    		
    		JList<String> listIngrediente = new JList<String>(ingredienteListModel);
    		listIngrediente.setValueIsAdjusting(true);
    		listIngrediente.setBounds(290, 100, 200, 200);
    		frame.getContentPane().add(listIngrediente);
    		
			JList<String> listWeight = new JList<String>(weightListModel);
    		listWeight.setValueIsAdjusting(true);
    		listWeight.setBounds(536, 100, 200, 200);
    		frame.getContentPane().add(listWeight);
    		
    		JMenuBar menuBar = new JMenuBar();
    		menuBar.setBounds(0, 0, 655, 31);
    		frame.getContentPane().add(menuBar);
    		
    		JMenu mnAction = new JMenu("Azione");
    		menuBar.add(mnAction);
    		
    		JMenuItem plsAdd = new JMenuItem("Aggiungi Ricetta");
    		mnAction.add(plsAdd);
    		
    		JMenuItem plsRmv = new JMenuItem("Rimuovi Ricetta");
    		mnAction.add(plsRmv);
    		
    		JMenuItem plsBack = new JMenuItem("Indietro");
    		mnAction.add(plsBack);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
