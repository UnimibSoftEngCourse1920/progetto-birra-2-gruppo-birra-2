package com.it.gruppo2.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
import javax.swing.SwingConstants;

public class BrewDayMenu {

	JFrame frame;
	private ArrayList<Integer> ricettArrayList = new ArrayList<Integer>();
	private ArrayList<Birra> birraList = new ArrayList<Birra>();
	private ArrayList<Ingrediente> ingredienteList = new ArrayList<Ingrediente>();
	private DefaultListModel<String> ingrList = new DefaultListModel<String>();
	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection, final Birraio brewerBirraio, final int login) {
		
		try (Statement stmt = connection.createStatement();Statement stmt1 = connection.createStatement()){
			
			String ldsList = new String();
			String sql = "SELECT distinct ingrediente.* FROM dispensa " +
					"INNER JOIN ingrediente ON dispensa.id_ingrediente = ingrediente.id_ingrediente " +
					"WHERE dispensa.qta < 1 AND dispensa.id_birraio ='" + brewerBirraio.getId_birraio()+ "'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				rs.beforeFirst();
				int i=0;
				while(rs.next()==true) {
					//inserisco tutte le birre in una lista
					ingredienteList.add(i,new Ingrediente(rs.getInt("id_ingrediente"), rs.getString("nome"), rs.getString("tipo")));
					ldsList+=(ingredienteList.get(i).getNome() + ", ");
					sql="UPDATE `brewdaydb`.`dispensa` SET `lds`='Y' WHERE  `id_ingrediente`='" + ingredienteList.get(i).getId_ingrediente() 
							+"' AND `id_birraio`='" + brewerBirraio.getId_birraio()+ "'";
					stmt1.executeUpdate(sql);
					ingrList.addElement(ingredienteList.get(i).getNome());
					i++;
				}
				
				int input = JOptionPane.showOptionDialog(frame, "Ingredienti bassi: " +ldsList, "Vuoi aggiungere alla lista della spesa?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if(input == JOptionPane.YES_OPTION)
				{
					ListaDellaSpesa lds = new ListaDellaSpesa(connection, brewerBirraio);
					lds.invokeGUI(connection, brewerBirraio);
					frame.dispose();
				}
				else
				{
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
				
			}
			else {
				BrewDayMenu window = new BrewDayMenu(connection, brewerBirraio);
				window.frame.setVisible(true);
			}
				
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		
		
		
	}
	
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
	private void initialize(final Connection connection, final Birraio brewerBirraio) throws SQLException{
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		Statement stmt;
		stmt = connection.createStatement();
		
		//creazione oggetti birra basati sul database

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
		DefaultListModel<String> noteList = new DefaultListModel<String>();
		
		if(rs.next())
		{
			for(int i=0; i<max; i++, rs.next()) {
				//inserisco tutte le birre in una lista
				birraList.add(i,new Birra(rs.getInt("id_birra"), rs.getString("nome"), rs.getString("note"), rs.getInt("id_birraio")));
				nomeList.addElement(birraList.get(i).getNome());
				noteList.addElement(birraList.get(i).getNote());
			}
		}else {
			nomeList.addElement("Non esiste alcuna birra!");
			noteList.addElement("");
		}
		
		rs.close();
		
		
		final JList<String> listd = new JList<String>(nomeList);
		listd.setBounds(64, 32, 200, 200);
		frame.getContentPane().add(listd);
		
		final JList<String> listr = new JList<String>(noteList);
		listr.setBounds(265, 32, 200, 200);
		frame.getContentPane().add(listr);

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
				CRUDoperationsRicetta newRic = new CRUDoperationsRicetta(connection, brewerBirraio, "newRic");
				newRic.invokeGUI(connection, brewerBirraio, "newRic");
				frame.dispose();
			}
		});
		mnRicetta.add(mntmNuovaRicetta);
		
		JMenuItem mntmEliminaRicetta = new JMenuItem("Elimina ricetta");
		mntmEliminaRicetta.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsRicetta newRic = new CRUDoperationsRicetta(connection, brewerBirraio, "delRic");
				newRic.invokeGUI(connection, brewerBirraio, "delRic");
				frame.dispose();
			}
		});
		mnRicetta.add(mntmEliminaRicetta);
		
		JMenuItem mntmModificaRicetta = new JMenuItem("Modifica ricetta");
		mntmModificaRicetta.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsRicetta newRic = new CRUDoperationsRicetta(connection, brewerBirraio, "modRic");
				newRic.invokeGUI(connection, brewerBirraio, "modRic");
				frame.dispose();
			}
		});
		mnRicetta.add(mntmModificaRicetta);
		
		JMenu mnIngredienti = new JMenu("Ingredienti");
		menuBar.add(mnIngredienti);
		
		JMenuItem mntmVisIngrDisp = new JMenuItem("Visualizza ingredienti");
		mntmVisIngrDisp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsIngrediente showIngr = new CRUDoperationsIngrediente(connection, brewerBirraio, "showIngr");
				showIngr.invokeGUI(connection, brewerBirraio, "showIngr");
				frame.dispose();
			}
		});
		mnIngredienti.add(mntmVisIngrDisp);
		
		JMenuItem mntmAggiungiIngrediente = new JMenuItem("Aggiungi ingrediente");
		mntmAggiungiIngrediente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsIngrediente showIngr = new CRUDoperationsIngrediente(connection, brewerBirraio, "addIngr");
				showIngr.invokeGUI(connection, brewerBirraio, "addIngr");
				frame.dispose();
			}
		});
		
		JMenuItem mntmEliminaIngrediente = new JMenuItem("Elimina Ingrediente");
		mntmEliminaIngrediente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsIngrediente showIngr = new CRUDoperationsIngrediente(connection, brewerBirraio, "delIngr");
				showIngr.invokeGUI(connection, brewerBirraio, "delIngr");
				frame.dispose();
			}
		});
		mnIngredienti.add(mntmEliminaIngrediente);
		mnIngredienti.add(mntmAggiungiIngrediente);
		
		JMenu mnBirra = new JMenu("Birra");
		menuBar.add(mnBirra);
		
		JMenuItem mntmEliminaBirra = new JMenuItem("Elimina Birra");
		mntmEliminaBirra.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsBirra crudBirra = new CRUDoperationsBirra(connection, brewerBirraio, "delBir");
				crudBirra.invokeGUI(connection, brewerBirraio, "delBir");
				frame.dispose();
			}
		});
		mnBirra.add(mntmEliminaBirra);
		
		JMenuItem mntmAggiungiBirra = new JMenuItem("Aggiungi Birra");
		mntmAggiungiBirra.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsBirra crudBirra = new CRUDoperationsBirra(connection, brewerBirraio, "aggBir");
				crudBirra.invokeGUI(connection, brewerBirraio, "aggBir");
				frame.dispose();
			}
		});
		mnBirra.add(mntmAggiungiBirra);
		
		JMenu mnAttrezzatura = new JMenu("Attrezzature");
		menuBar.add(mnAttrezzatura);
		
		JMenuItem mntmNuovaAttrezzatura = new JMenuItem("Nuova attrezzatura");
		mntmNuovaAttrezzatura.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsAttrezzatura newRic = new CRUDoperationsAttrezzatura(connection, brewerBirraio, "addAttr");
				newRic.invokeGUI(connection, brewerBirraio, "addAttr");
				frame.dispose();
			}
		});
		mnAttrezzatura.add(mntmNuovaAttrezzatura);
		
		JMenuItem mntmMostraAttrezzatura = new JMenuItem("Mostra attrezzatura");
		mntmMostraAttrezzatura.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsAttrezzatura newRic = new CRUDoperationsAttrezzatura(connection, brewerBirraio, "showAttr");
				newRic.invokeGUI(connection, brewerBirraio, "showAttr");
				frame.dispose();
			}
		});
		mnAttrezzatura.add(mntmMostraAttrezzatura);
		
		JMenuItem mntmEliminaAttrezzatura = new JMenuItem("Elimina attrezzatura");
		mntmEliminaAttrezzatura.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CRUDoperationsAttrezzatura newRic = new CRUDoperationsAttrezzatura(connection, brewerBirraio, "delAttr");
				newRic.invokeGUI(connection, brewerBirraio, "delAttr");
				frame.dispose();
			}
		});
		mnAttrezzatura.add(mntmEliminaAttrezzatura);
	
		
		
		JMenu mnLista = new JMenu("Lista della spesa");
		menuBar.add(mnLista);
		
		JMenuItem mntmVediLista = new JMenuItem("Vedi lista ");
		mntmVediLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				ListaDellaSpesa lds = new ListaDellaSpesa(connection, brewerBirraio);
				lds.invokeGUI(connection, brewerBirraio);
				frame.dispose();
			}
		});
		mntmVediLista.setHorizontalAlignment(SwingConstants.TRAILING);
		mnLista.add(mntmVediLista);
		
		JMenu mnProfilo = new JMenu("Profilo");
		menuBar.add(mnProfilo);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Login login = new Login(connection);
				login.invokeGUI(connection);
				frame.dispose();
			}
		});
		mntmLogout.setHorizontalAlignment(SwingConstants.TRAILING);
		mnProfilo.add(mntmLogout);
		
			
		JMenu mnWSIBT = new JMenu("WSIBT");
		menuBar.add(mnWSIBT);
		
		JMenuItem mntmConsigliami = new JMenuItem("Consigliami");	
		mntmConsigliami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Statement stmt = connection.createStatement();){
					
		    		//String sql = "SELECT ricetta.* FROM ricetta INNER JOIN birra ON ricetta.id_birra = birra.id_birra WHERE birra.id_birraio = "+brewerBirraio.getId_birraio()+" GROUP BY id_ricetta HAVING COUNT(id_ingrediente) = (SELECT COUNT(id_ingrediente) FROM ricetta where id_ingrediente = ANY(SELECT DISTINCT dispensa.id_ingrediente FROM dispensa WHERE dispensa.qta > 0))";
					String sql = "SELECT id_ricetta" + 
							"							FROM ricetta INNER JOIN birra ON ricetta.id_birra = birra.id_birra  " + 
							"							WHERE birra.id_birraio = "+brewerBirraio.getId_birraio()+" GROUP BY id_ricetta " + 
							"							HAVING (id_ricetta,COUNT(id_ingrediente)) = ANY(SELECT id_ricetta,COUNT(id_ingrediente)" + 
							"							FROM ricetta INNER JOIN birra ON birra.id_birra = ricetta.id_birra " + 
							"	              			where birra.id_birraio = "+brewerBirraio.getId_birraio()+" AND id_ingrediente = ANY(SELECT DISTINCT dispensa.id_ingrediente" + 
							"							FROM dispensa INNER JOIN ingrediente ON ingrediente.id_ingrediente = dispensa.id_ingrediente INNER JOIN ricetta ON ricetta.id_ingrediente = ingrediente.id_ingrediente" + 
							"							WHERE dispensa.qta > 0 AND ricetta.quantita<=dispensa.qta)" + 
							"							GROUP BY id_ricetta)";
					try (ResultSet rSet=stmt.executeQuery(sql);){
						int j=0;
			    		while(rSet.next())
			    		{	
								ricettArrayList.add(j,rSet.getInt("id_ricetta"));
								j++;
			    		}
						WSIBT grapInterf = new WSIBT(connection, brewerBirraio, ricettArrayList);
						grapInterf.invokeGUI(connection, brewerBirraio, ricettArrayList);
						frame.dispose();
					} catch (Exception e2) {
						// TODO: handle exception
					}
		    		
		    		} 
				catch (SQLException e1) {
					//
					e1.printStackTrace();
				}
				
			}
		});
		mnWSIBT.add(mntmConsigliami);
		
	}
}