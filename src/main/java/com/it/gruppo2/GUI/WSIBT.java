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
	private ArrayList<Ricetta> ingrRicettaFinale = new ArrayList<Ricetta>();
	private DefaultListModel<String> nomiRicetta = new DefaultListModel<String>();
	private DefaultListModel<String> quantitaTotRicetta = new DefaultListModel<String>();
	private ArrayList<Integer> idRicetta = new ArrayList<Integer>();
	
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
			int f=0, g=0,z=0;
			stmt = connection.createStatement();
			//prendo la capacità massima dell'attrazzatura del birraio 
			String sql = "SELECT attrezzatura.capacita FROM attrezzatura WHERE disponibilita = 'Y' AND id_birraio= "+brewerBirraio.getId_birraio()+" ORDER BY capacita DESC LIMIT 1";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				capienzaAttr=rs.getInt("capacita");
			}
			rs.close();
			//passo a visionare tutte le ricette che hanno gli ingredienti >0
			while(f<ricettArrayList.size()) 
			{
				stmt1 = connection.createStatement();
				//prendo tutti gli ingredienti della ricetta che sto visitando
				sql = "SELECT * FROM ricetta WHERE id_ricetta = " +ricettArrayList.get(f);
				ResultSet rs1 = stmt1.executeQuery(sql);
				
<<<<<<< HEAD
				int j=0;
				while(rs1.next()){
					//inserisco tutti gli ingredienti dentro un array
					ingrRicetta.add(j,new Ricetta(rs1.getInt("id_ricetta"),rs1.getDouble("quantita"),rs1.getInt("id_birra"),rs1.getInt("id_ingrediente"), rs1.getString("nome"), rs1.getDouble("quantitaPercentuale")));
=======
				int j=0, k=0;
				while(rs1.next()){	
					ingrRicetta.add(j,new Ricetta(rs1.getInt("id_ricetta"),rs1.getDouble("quantita"),rs1.getInt("id_birra"),rs1.getInt("id_ingrediente"), rs1.getString("nome"), 1));
>>>>>>> branch 'master' of https://github.com/UnimibSoftEngCourse1920/progetto-birra-2-gruppo-birra-2.git
					j++;
					
	    		}
				rs1.close();
				
				int k=0;
				ArrayList<Double> iterazioniTot = new ArrayList<Double>();
				//vado a visitare tittu gli ingredienti di tutte le ricette per vedere quale sarà la moltiplicazione da fare
				while(k<ingrRicetta.size())
				{
					System.out.println("ciao");
					stmt2 = connection.createStatement();
					if(ricettArrayList.get(f) == ingrRicetta.get(k).getId_ricetta()) //seleziono solo gli ingredienti di quella ricetta
					{
						//seleziono la quantita di quell'ingrediente in dispensa
						sql = "SELECT qta FROM dispensa WHERE id_ingrediente="+ingrRicetta.get(k).getId_ingrediente();
						ResultSet rs2 = stmt2.executeQuery(sql);
						if(rs2.next())
						{
							double iterazioni = Math.floor(rs2.getDouble("qta")/ingrRicetta.get(k).getQuantita());
							iterazioniTot.add(iterazioni);
							System.out.print(iterazioni);
			    		}
						rs2.close();
					}
					k++;
				}
				k=0;Double iterazioneMIN = Double.MAX_VALUE;
				//prendo l'iterazione minima che posso fare
				while(k<iterazioniTot.size()) {
					if(iterazioniTot.get(k)<iterazioneMIN) {
						iterazioneMIN = iterazioniTot.get(k);
					}
					k++;
				}
				quantitaRicettaTot=0;
				k=0;
				//vado a prendere la quantità tot che verrà prodotta da quella ricetta
				while(k<ingrRicetta.size())
				{
					if(ricettArrayList.get(f) == ingrRicetta.get(k).getId_ricetta()) //seleziono solo gli ingredienti di quella ricetta
					{
						if(quantitaRicettaTot<=capienzaAttr){
							quantitaRicettaTot += (ingrRicetta.get(k).getQuantita()*iterazioneMIN);
			    		}
					}
					k++;
				}
				
				//posso procedere ad inserire la ricetta f
				if(quantitaRicettaTot<capienzaAttr)
				{
					k=0;
					//visito tutti gli ingredienti
					while(k<ingrRicetta.size()) {
						if(ricettArrayList.get(f) == ingrRicetta.get(k).getId_ricetta()) //seleziono solo gli ingredienti di quella ricetta
						{
							Double quantityDouble = (ingrRicetta.get(k).getQuantita()*iterazioneMIN);
							//inserisco l'ingrediente nella lista
							ingrRicettaFinale.add(z, new Ricetta(ingrRicetta.get(k).getId_ricetta(), quantityDouble, 0,ingrRicetta.get(k).getId_ingrediente(),null,0));
							z++;			
						}
						k++;
					}
					//devo inserire il nome della ricetta che sto inserendo
					try (Statement stmt5 = connection.createStatement();){
						sql = "SELECT DISTINCT nome FROM ricetta WHERE id_ricetta = "+ricettArrayList.get(f);
						try (ResultSet rSet = stmt5.executeQuery(sql);){
							if(rSet.next()){
								System.out.println(rSet.getString("nome"));
								//inserisco il nome della ricetta alla posizione g
								nomiRicetta.addElement(rSet.getString("nome"));
								//inserisco l'id della ricetta alla posizione g
								idRicetta.add(g, ricettArrayList.get(f));
								//
								quantitaTotRicetta.addElement(Double.toString(quantitaRicettaTot));
								g++;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				f++;
			}
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//crazione oggetti birra basati sul database...
		final JList<String> listWSITB = new JList<String>(nomiRicetta);
		listWSITB.setBounds(154, 92, 200, 200);
		frame.getContentPane().add(listWSITB);

		
		JButton btnPrepara = new JButton("Prepara");
		btnPrepara.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				if(listWSITB.getSelectedValue()==null)
					JOptionPane.showMessageDialog(frame, "Seleziona una ricetta!");
				else {
					int input = JOptionPane.showOptionDialog(frame, "Sei sicuro di voler preparare " +listWSITB.getSelectedValue()+"?", "WSIBT", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);				    
					//id della ricetta selezionata
					int idRicettaSelezionata = idRicetta.get(listWSITB.getSelectedIndex());
					if(input == JOptionPane.YES_OPTION){
						int h=0;
						//aggiornare le quantità tutti gli ingredienti di quella ricetta
						while(h<ingrRicettaFinale.size()) {
							if(ingrRicettaFinale.get(h).getId_ricetta() == idRicettaSelezionata) {
								try (Statement stmt4 = connection.createStatement();){
									String sql="UPDATE dispensa SET qta = (qta - "+ingrRicettaFinale.get(h).getQuantita()+") WHERE id_ingrediente= "+ingrRicettaFinale.get(h).getId_ingrediente()+" AND id_birraio = "+brewerBirraio.getId_birraio();                 
									stmt4.executeUpdate(sql);
								} catch (Exception e2) {
									// TODO: handle exception
								}
								
							}
							h++;
						}
						BrewDayMenu bDayMenu = new BrewDayMenu(connection, brewerBirraio);
						bDayMenu.invokeGUI(connection, brewerBirraio, 1);
						frame.dispose();
						
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
		
		JList<String> listQtaTot = new JList<String>(quantitaTotRicetta);
		listQtaTot.setBounds(413, 92, 200, 200);
		frame.getContentPane().add(listQtaTot);
		
		
		
	

			
	}

