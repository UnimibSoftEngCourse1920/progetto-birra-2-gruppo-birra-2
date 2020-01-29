package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.it.gruppo2.brewDay2.Birraio;
import com.it.gruppo2.brewDay2.Ricetta;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class WSIBT {

	private JFrame frame;
	private double capienzaAttr=0, quantitaRicettaTot=0;
	private ArrayList<Ricetta> ingrRicetta = new ArrayList<Ricetta>();
	private DefaultListModel<String> nomiRicetta = new DefaultListModel<String>();
	
	public void invokeGUI(final Connection connection, final Birraio brewerBirraio, final ArrayList<Integer> ricettArrayList) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WSIBT window = new WSIBT(connection, brewerBirraio, ricettArrayList);
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
	public WSIBT(Connection connection, Birraio brewerBirraio, final ArrayList<Integer> ricettArrayList) throws SQLException {
		
		initialize(connection, brewerBirraio, ricettArrayList);
	}
	
	private void initialize(final Connection connection, final Birraio brewerBirraio, final ArrayList<Integer> ricettArrayList) {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTitolo = new JLabel("Ricetta consigiata:");
		lblTitolo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitolo.setBounds(295, 37, 204, 55);
		frame.getContentPane().add(lblTitolo);
		
		
		Statement stmt, stmt1, stmt2;
		try {
			int f=0, g=0;
			while(f<ricettArrayList.size()) {
				quantitaRicettaTot=0;
				stmt = connection.createStatement();
				//prendo la capacità massima dell'attrazzatura del birraio 
				String sql = "SELECT attrezzatura.capacita FROM attrezzatura WHERE disponibilita = 'Y' AND id_birraio= "+brewerBirraio.getId_birraio()+" ORDER BY capacita DESC LIMIT 1";
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()) {
					capienzaAttr=rs.getInt("capacita");
				}
				rs.close();
				
				stmt1 = connection.createStatement();
				//prendo tutti gli ingredienti delle ricette che rispettavano la quantità>0
				sql = "SELECT * FROM ricetta WHERE id_ricetta = " +ricettArrayList.get(f);
				ResultSet rs1 = stmt1.executeQuery(sql);
				
				int j=0, k=0;
				while(rs1.next()){	
					ingrRicetta.add(j,new Ricetta(rs1.getInt("id_ricetta"),rs1.getDouble("quantita"),rs1.getInt("id_birra"),rs1.getInt("id_ingrediente"), rs1.getString("nome")));
					j++;
		
	    		}
				
				rs1.close();
				
				while(k<j) {
					stmt2 = connection.createStatement();
					sql = "SELECT qta FROM dispensa WHERE id_ingrediente="+ingrRicetta.get(k).getId_ingrediente();
					ResultSet rs2 = stmt2.executeQuery(sql);
					
					while(rs2.next() && quantitaRicettaTot<=capienzaAttr){	
						quantitaRicettaTot += (ingrRicetta.get(k).getQuantita() * rs2.getDouble("qta"));

		    		}
					k++;
					rs2.close();
				}
				
				if(quantitaRicettaTot<capienzaAttr){
					//mi salvo l'id della ricetta che va bene 
					nomiRicetta.add(g, ingrRicetta.get(0).getNome());
					//creazione array nomiRicetta con dentro le ricette che vanno bene davvero
					
					g++;
				}
				f++;
		}
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//crazione oggetti birra basati sul database...
		final JList<String> listWSITB = new JList<String>(nomiRicetta);
		listWSITB.setBounds(295, 93, 200, 200);
		frame.getContentPane().add(listWSITB);

		
		JButton btnPrepara = new JButton("Prepara");
		btnPrepara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(listWSITB.getSelectedValue()==null)
					JOptionPane.showMessageDialog(frame, "Seleziona una ricetta!");
				else {
					int input = JOptionPane.showOptionDialog(frame, "Sei sicuro di voler preparare " +listWSITB.getSelectedValue()+"?", "WSIBT", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);				    
					if(input == JOptionPane.YES_OPTION){
						int h=0;
						Statement stmt4;
						try {
							while(h<ingrRicetta.size()) {
								String sql="UPDATE dispensa SET qta='"+ingrRicetta.get(h).getQuantita()+"' WHERE id_ingrediente='"+ingrRicetta.get(h).getId_ingrediente()+"' AND `id_birraio`='"+brewerBirraio.getId_birraio()+"';";                 
								stmt4 = connection.createStatement();
								stmt4.executeUpdate(sql);
								
								BrewDayMenu bDayMenu = new BrewDayMenu(connection, brewerBirraio);
								bDayMenu.invokeGUI(connection, brewerBirraio, 1);
								frame.dispose();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				}
			}
		});
		btnPrepara.setBounds(295, 326, 204, 23);
		frame.getContentPane().add(btnPrepara);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmIndietro = new JMenuItem("Indietro");
		mntmIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BrewDayMenu bDayMenu = new BrewDayMenu(connection, brewerBirraio);
				bDayMenu.invokeGUI(connection, brewerBirraio);
				frame.dispose();
			}
		});
		menuBar.add(mntmIndietro);
		frame.getContentPane().setLayout(null);
		
		
		
	

			
	}

	}

