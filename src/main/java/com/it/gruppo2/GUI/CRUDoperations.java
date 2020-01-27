package com.it.gruppo2.GUI;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import com.it.gruppo2.brewDay2.Birraio;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;

public class CRUDoperations {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection, final Birraio birraio, final String operation) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUDoperations window = new CRUDoperations(connection, birraio, operation);
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
	public CRUDoperations(Connection connection, Birraio birraio, String operation) {
		try {
			initialize(connection, birraio, operation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize(final Connection connection, final Birraio birraio, String operation) throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 840, 432);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmIndietro = new JMenuItem("Indietro");
		mntmIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
				bDayMenu.invokeGUI(connection, birraio);
				frame.dispose();
			}
		});
		menuBar.add(mntmIndietro);
		frame.getContentPane().setLayout(null);
		
		if(operation == "showIngr") {
			Statement stmt;
			stmt = connection.createStatement();
			
			//visualizza ingredienti basati sul birraio
	
			String sql = "SELECT ingrediente.nome AS nome, dispensa.qta AS qta FROM dispensa INNER JOIN ingrediente ON ingrediente.id_ingrediente = dispensa.id_ingrediente WHERE dispensa.id_birraio = '" + birraio.getId_birraio() + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			DefaultListModel<String> nomeList = new DefaultListModel<String>();
			DefaultListModel<Double> qtaList = new DefaultListModel<Double>();
			while(rs.next())
			{
					nomeList.addElement(rs.getString("nome"));
					qtaList.addElement(rs.getDouble("qta"));
			}
			
			JList<String> listIngrName = new JList<String>(nomeList);
			listIngrName.setBounds(49, 16, 200, 311);
			frame.getContentPane().add(listIngrName);
			
			JList<Double> listCapienza = new JList<Double>(qtaList);
			listCapienza.setBounds(295, 16, 200, 311);
			frame.getContentPane().add(listCapienza);
			rs.close();
		}
		
	}
}
