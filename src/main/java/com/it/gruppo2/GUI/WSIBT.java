package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.it.gruppo2.brewDay2.Attrezzatura;
import com.it.gruppo2.brewDay2.Birraio;
import com.it.gruppo2.brewDay2.Ricetta;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WSIBT {

	private JFrame frame;
	private ArrayList<Integer> wsibtArrayList = new ArrayList<Integer>();

	public void invokeGUI(final Connection connection, final Birraio brewerBirraio, final ArrayList<Ricetta> ricettArrayList, final ArrayList<Attrezzatura> attrezzaturaArrayList) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WSIBT window = new WSIBT(connection, brewerBirraio, ricettArrayList, attrezzaturaArrayList);
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
	public WSIBT(Connection connection, Birraio brewerBirraio, final ArrayList<Ricetta> ricettArrayList, final ArrayList<Attrezzatura> attrezzaturaArrayList) throws SQLException {
		initialize(connection, brewerBirraio, ricettArrayList, attrezzaturaArrayList);
	}
	
	private void initialize(final Connection connection, final Birraio brewerBirraio, final ArrayList<Ricetta> ricettArrayList, final ArrayList<Attrezzatura> attrezzaturaArrayList) {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblTitolo = new JLabel("Ricetta consigiata:");
		lblTitolo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitolo.setBounds(295, 37, 204, 55);
		frame.getContentPane().add(lblTitolo);
		
		final JLabel lblWSIBT = new JLabel("Esempio");
		lblWSIBT.setHorizontalAlignment(SwingConstants.CENTER);
		lblWSIBT.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblWSIBT.setBounds(0, 164, 804, 71);
		frame.getContentPane().add(lblWSIBT);
		
		JButton btnVediRicetta = new JButton("Vedi ricetta");
		btnVediRicetta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "BIRRA");
			}
		});
		btnVediRicetta.setBounds(295, 326, 89, 23);
		frame.getContentPane().add(btnVediRicetta);
		
		JButton btnProssima = new JButton("Prossima");
		btnProssima.setBounds(410, 326, 89, 23);
		frame.getContentPane().add(btnProssima);
		
		Statement stmt;
		try {
			
			
			stmt = connection.createStatement();
			ResultSet rs;
			String sql = "SELECT distinct ricetta.* FROM ricetta " +
					"INNER JOIN ingrediente ON ingrediente.id_ingrediente = ricetta.id_ingrediente " +
					"INNER JOIN dispensa ON dispensa.id_ingrediente = ingrediente.id_ingrediente "+
					"INNER JOIN birraio ON birraio.id_birraio =  dispensa.id_birraio " +
					"INNER JOIN attrezzatura ON attrezzatura.id_birraio= birraio.id_birraio " +
					"WHERE ricetta.quantita < dispensa.qta " +
					"HAVING ( " +
					"SELECT COUNT(ricetta.id_ingrediente) " +
					"FROM ricetta " +
					"INNER JOIN ingrediente ON ingrediente.id_ingrediente = ricetta.id_ingrediente " +
					"INNER JOIN dispensa ON dispensa.id_ingrediente = ingrediente.id_ingrediente " +
					"WHERE ricetta.quantita < dispensa.qta " +
					"GROUP BY id_ricetta)=4; ";
							

			rs = stmt.executeQuery(sql);
			
			int i=0;
			while(rs.next()) {
    				wsibtArrayList.add(i, rs.getInt("ricetta.id_birra"));
    				i++;
    		}
			rs.close();
			
			Statement stmt1=connection.createStatement();
			sql = "SELECT nome FROM birra WHERE id_birra = 1";
			ResultSet rs1;
			rs1 = stmt1.executeQuery(sql);
			if(rs1.next())
				lblWSIBT.setText(rs1.getString("nome"));
			rs1.close();
				
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
			
		}
		

			
	}

	}

