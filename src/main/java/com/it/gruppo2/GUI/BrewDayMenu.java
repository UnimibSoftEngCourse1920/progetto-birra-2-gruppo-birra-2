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
		DefaultListModel<String> nomeList = new DefaultListModel<String>();
		DefaultListModel<String> tipoList = new DefaultListModel<String>();
		if(rs.next())
		{
			for(int i=0; i<max; i++, rs.next()) {
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
                RicetteBirra ricetteBirra = new RicetteBirra(connection, birraList.get(index));
                ricetteBirra.invokeGUI(connection, birraList.get(index));
                frame.dispose();
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
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Log out");
		mnProfilo.add(mntmNewMenuItem);
	}
}
