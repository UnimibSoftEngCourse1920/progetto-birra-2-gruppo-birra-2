package com.it.gruppo2.GUI;

import java.awt.EventQueue;  
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.it.gruppo2.brewDay2.Birra;
import com.it.gruppo2.brewDay2.Birraio;
import com.it.gruppo2.brewDay2.Ingrediente;
import com.it.gruppo2.brewDay2.Ricetta;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class CRUDoperationsRicetta {

	private JFrame frame;
	private JTextField txtNome;
	private JTextField txtQta;
	private JComboBox<String> comboBirra;
	private JComboBox<String> comboIngrediente;
	private JComboBox<String> comboRicetta;
	private ArrayList<Birra> birraList;
	private ArrayList<Ingrediente> ingredienteList;
	private ArrayList<Ricetta> ricettaList;
	
	
	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection, final Birraio birraio, final String operation) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					CRUDoperationsRicetta window = new CRUDoperationsRicetta(connection, birraio, operation);
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
	public CRUDoperationsRicetta(Connection connection, Birraio birraio, String operation) {
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//funzione principale di smistamento
	protected void initialize(final Connection connection, final Birraio birraio, final String operation) throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
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
		
		if(operation.equals("delRic")) {
			JLabel lblSelezionaBirra = new JLabel("Seleziona Birra");
			lblSelezionaBirra.setBounds(82, 61, 161, 20);
			frame.getContentPane().add(lblSelezionaBirra);
			
			ArrayList<String> arrayList = new ArrayList<String>();
			
			birraList = new ArrayList<Birra>();
			try (Statement stmt = connection.createStatement()){
				String sql = "SELECT birra.id_birra AS id, birra.nome AS nome FROM birra INNER JOIN birraio ON birra.id_birraio = birraio.id_birraio WHERE birraio.id_birraio = '"+ birraio.getId_birraio() +"'";
				
				try (ResultSet rs = stmt.executeQuery(sql);){
					//seleziono tutte le birre
					int i = 0;
					while(rs.next())
					{
						birraList.add(new Birra(rs.getInt("id"), rs.getString("nome"), null, 0));
						arrayList.add(birraList.get(i).getNome());
						i++;
					}
					rs.close();
				} catch (Exception e) {
					
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			comboBirra = new JComboBox(arrayList.toArray());
			comboBirra.setBounds(258, 58, 267, 26);
			frame.getContentPane().add(comboBirra);
			
			JButton btneliminandoRicetta = new JButton("Eliminando Ricetta");
			btneliminandoRicetta.setBounds(581, 57, 267, 29);
			btneliminandoRicetta.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int id_birra = birraList.get(comboBirra.getSelectedIndex()).getId_birra();
					initialize(connection, birraio, id_birra, operation);
					frame.dispose();
				}
			});
			frame.getContentPane().add(btneliminandoRicetta);
		}
		if(operation.equals("newRic")) {
			JLabel lblNomeIn = new JLabel("Nome Ricetta");
			lblNomeIn.setBounds(77, 128, 182, 20);
			frame.getContentPane().add(lblNomeIn);
			
			txtNome = new JTextField();
			txtNome.setBounds(305, 125, 267, 26);
			frame.getContentPane().add(txtNome);
			txtNome.setColumns(10);
			
			JLabel lblBirra = new JLabel("Seleziona Birra");
			lblBirra.setBounds(77, 67, 182, 20);
			frame.getContentPane().add(lblBirra);
			
			ArrayList<String> arrayList = new ArrayList<String>();
			
			birraList = new ArrayList<Birra>();
			try (Statement stmt = connection.createStatement()){
				//verifica che non esista un altro ingrediente uguale
				String sql = "SELECT birra.id_birra AS id, birra.nome AS nome FROM birra INNER JOIN birraio ON birra.id_birraio = birraio.id_birraio WHERE birraio.id_birraio = '"+ birraio.getId_birraio() +"'";
				try (ResultSet rs = stmt.executeQuery(sql);){
					int i = 0;
					while(rs.next())
					{
						birraList.add(new Birra(rs.getInt("id"), rs.getString("nome"), null, 0));
						arrayList.add(birraList.get(i).getNome());
						i++;
					}
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			comboBirra = new JComboBox(arrayList.toArray());
			comboBirra.setBounds(305, 64, 267, 26);
			frame.getContentPane().add(comboBirra);
			
			JButton btnCreaIngrediente = new JButton("Crea Ricetta");
			btnCreaIngrediente.setBounds(600, 124, 221, 29);
			btnCreaIngrediente.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int id_birra = birraList.get(comboBirra.getSelectedIndex()).getId_birra();
					initialize(connection, birraio, txtNome.getText(), id_birra);
					frame.dispose();
				}
			});
			frame.getContentPane().add(btnCreaIngrediente);
		}
		if(operation.equals("modRic")) {
			JLabel lblSelezionaBirra = new JLabel("Seleziona Birra");
			lblSelezionaBirra.setBounds(82, 61, 161, 20);
			frame.getContentPane().add(lblSelezionaBirra);
			
			ArrayList<String> arrayList = new ArrayList<String>();
			birraList = new ArrayList<Birra>();
			try (Statement stmt = connection.createStatement();){
				
				//seleziono tutte le birre
				String sql = "SELECT birra.id_birra AS id, birra.nome AS nome FROM birra INNER JOIN birraio ON birra.id_birraio = birraio.id_birraio WHERE birraio.id_birraio = '"+ birraio.getId_birraio() +"'";
				try (ResultSet rs = stmt.executeQuery(sql);){
					int i = 0;
					while(rs.next())
					{
						birraList.add(new Birra(rs.getInt("id"), rs.getString("nome"), null, 0));
						arrayList.add(birraList.get(i).getNome());
						i++;
					}
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			comboBirra = new JComboBox(arrayList.toArray());
			comboBirra.setBounds(258, 58, 267, 26);
			frame.getContentPane().add(comboBirra);
			
			JButton btnmodificandoRicetta = new JButton("Modificando Ricetta");
			btnmodificandoRicetta.setBounds(581, 57, 267, 29);
			btnmodificandoRicetta.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int id_birra = birraList.get(comboBirra.getSelectedIndex()).getId_birra();
					initialize(connection, birraio, id_birra, operation);
					frame.dispose();
				}
			});
			frame.getContentPane().add(btnmodificandoRicetta);
		}
	}
	//funzione di supporto per la creazione di una ricetta
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initialize(final Connection connection, final Birraio birraio, final String nomeRicetta, final int id_birra) {  
		final JFrame frame1 = new JFrame();
		frame1.setVisible(true);
		frame1.setBounds(100, 100, 900, 600);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame1.setJMenuBar(menuBar);
		
		JMenuItem mntmIndietro = new JMenuItem("Indietro");
		mntmIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try (Statement stmt = connection.createStatement();){
					//prendo tutti gli ingredienti e le loro quantita inserite in kg
					String sql = "SELECT ricetta.id_ingrediente AS id_ingrediente, ricetta.quantita AS qta, ricetta.id_ricetta AS id_ricetta FROM ricetta WHERE ricetta.id_birra = "+ id_birra +"  AND ricetta.nome = '"+nomeRicetta+"'";
					try (ResultSet rs = stmt.executeQuery(sql);){
						if(!rs.next())
						{
							System.out.print("Nessun ingrediente inserito");
						}else 
						{
							ArrayList<Ricetta> ricettas = new ArrayList<Ricetta>();
							rs.beforeFirst();
							double quantitaTot = 0;
							while(rs.next()) {
								ricettas.add(new Ricetta(rs.getInt("id_ricetta"), rs.getDouble("qta"), id_birra, rs.getInt("id_ingrediente"), nomeRicetta,0));
								quantitaTot += rs.getDouble("qta");
							}
							int i=0;
							while (i<ricettas.size()) {
								ricettas.get(i).setQuantitaPercentuale((ricettas.get(i).getQuantita()*100)/quantitaTot);
								i++;
							}
							i=0;
							while (i<ricettas.size()) {
								Statement stmt1 = connection.createStatement();
								sql = "UPDATE ricetta SET id_ricetta = "+ricettas.get(i).getId_ricetta()+",id_ingrediente = "+ricettas.get(i).getId_ingrediente()+",id_birra = "+ricettas.get(i).getId_birra()+",quantita = "+ricettas.get(i).getQuantita()+",nome = '"+ricettas.get(i).getNome()+"', quantitaPercentuale = "+ricettas.get(i).getQuantitaPercentuale()+" WHERE ricetta.id_ricetta = "+ricettas.get(i).getId_ricetta()+" AND ricetta.id_birra = "+id_birra+" AND ricetta.id_ingrediente = "+ricettas.get(i).getId_ingrediente();
								stmt1.executeUpdate(sql);
								i++;
							}
							
						}
					} catch (Exception e1) {
						// TODO: handle exception
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				CRUDoperationsRicetta cRicetta = new CRUDoperationsRicetta(connection, birraio, "newRic");
				cRicetta.invokeGUI(connection, birraio, "newRic");
				frame1.dispose();
			}
		});
		menuBar.add(mntmIndietro);
		frame1.getContentPane().setLayout(null);
		
		JLabel lblQta = new JLabel("Quantità in Kg");
		lblQta.setBounds(77, 128, 182, 20);
		frame1.getContentPane().add(lblQta);
		System.out.print(nomeRicetta);
		System.out.print(id_birra);
		txtQta = new JTextField();
		txtQta.setBounds(305, 125, 267, 26);
		frame1.getContentPane().add(txtQta);
		txtQta.setColumns(10);
		
		JLabel lblIngrediente = new JLabel("Seleziona Ingrediente");
		lblIngrediente.setBounds(77, 67, 182, 20);
		frame1.getContentPane().add(lblIngrediente);
		
		ArrayList<String> arrayList = new ArrayList<String>();
		ingredienteList = new ArrayList<Ingrediente>();
		try (Statement stmt = connection.createStatement();){
			//prendo tutti gli ingredienti che non appaertengono alla ricetta
			String sql = "SELECT ingrediente.id_ingrediente AS id, ingrediente.nome AS nome FROM dispensa INNER JOIN ingrediente ON ingrediente.id_ingrediente = dispensa.id_ingrediente WHERE dispensa.id_birraio = '"+ birraio.getId_birraio() +"'  AND ingrediente.id_ingrediente != ALL(SELECT id_ingrediente FROM ricetta WHERE nome = '"+nomeRicetta+"' AND id_birra = '"+id_birra+"')";
			try (ResultSet rs = stmt.executeQuery(sql);){
				int i = 0;
				if(!rs.first())
				{
					try (Statement stmt1 = connection.createStatement();){
						
						//prendo tutti gli ingredienti e le loro quantita inserite in kg
						sql = "SELECT ricetta.id_ingrediente AS id_ingrediente, ricetta.quantita AS qta, ricetta.id_ricetta AS id_ricetta FROM ricetta WHERE ricetta.id_birra = "+ id_birra +"  AND ricetta.nome = '"+nomeRicetta+"'";
						try (ResultSet rs1 = stmt1.executeQuery(sql);){
							if(!rs1.first())
							{
								System.out.print("Nessun ingrediente inserito");
							}else 
							{
								ArrayList<Ricetta> ricettas = new ArrayList<Ricetta>();
								rs1.beforeFirst();
								double quantitaTot = 0;
								while(rs1.next()) {
									ricettas.add(new Ricetta(rs1.getInt("id_ricetta"), rs1.getDouble("qta"), id_birra, rs1.getInt("id_ingrediente"), nomeRicetta,0));
									quantitaTot += rs1.getDouble("qta");
								}
								i=0;
								while (i<ricettas.size()) {
									ricettas.get(i).setQuantitaPercentuale((ricettas.get(i).getQuantita()*100)/quantitaTot);
									i++;
								}
								i=0;
								while (i<ricettas.size()) {
									sql = "UPDATE ricetta SET id_ricetta = "+ricettas.get(i).getId_ricetta()+",id_ingrediente = "+ricettas.get(i).getId_ingrediente()+",id_birra = "+ricettas.get(i).getId_birra()+",quantita = "+ricettas.get(i).getQuantita()+",nome = '"+ricettas.get(i).getNome()+"', quantitaPercentuale = "+ricettas.get(i).getQuantitaPercentuale()+" WHERE ricetta.id_ricetta = "+ricettas.get(i).getId_ricetta()+" AND ricetta.id_birra = "+id_birra+" AND ricetta.id_ingrediente = "+ricettas.get(i).getId_ingrediente();
									stmt.executeUpdate(sql);
									i++;
								}
							}
						} catch (Exception e1) {
							// TODO: handle exception
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					CRUDoperationsRicetta cRicetta = new CRUDoperationsRicetta(connection, birraio, "newRic");
					cRicetta.invokeGUI(connection, birraio, "newRic");
					frame1.dispose();
				}else {
					rs.beforeFirst();
					while(rs.next())
					{
						ingredienteList.add(new Ingrediente(rs.getInt("id"), rs.getString("nome"), null));
						arrayList.add(ingredienteList.get(i).getNome());
						i++;
					}
					rs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		comboIngrediente = new JComboBox(arrayList.toArray());
		comboIngrediente.setBounds(305, 64, 267, 26);
		frame1.getContentPane().add(comboIngrediente);
		
		JButton btnCreaIngrediente = new JButton("Aggiungi Ingrediente");
		btnCreaIngrediente.setBounds(600, 124, 221, 29);
		btnCreaIngrediente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				try (Statement stmt = connection.createStatement();){
					
					//verifica che non vengano superate le quanità massime
					int id_ingr = ingredienteList.get(comboIngrediente.getSelectedIndex()).getId_ingrediente();
					System.out.println(Double.valueOf(txtQta.getText()));
					String sql = "SELECT dispensa.qta AS qta FROM dispensa WHERE dispensa.id_ingrediente = '"+ id_ingr +"' AND dispensa.id_birraio = '" + birraio.getId_birraio() + "'";
					try (ResultSet rs = stmt.executeQuery(sql); Statement stmt1 = connection.createStatement();){
						if(rs.next())
						{
							if(rs.getDouble("qta") < Double.valueOf(txtQta.getText())) {
								JOptionPane.showMessageDialog(frame, "SFORATO LA QUANTITA' MASSIMA");
							}
							else {
								//verifico se la ricetta esiste già
								sql = "SELECT DISTINCT id_ricetta AS id FROM ricetta WHERE nome = '"+ nomeRicetta +"' AND id_birra = '"+id_birra+"'";
								try (ResultSet rs1 = stmt1.executeQuery(sql);){
									if(rs1.next())
									{
										sql = "INSERT INTO ricetta (id_ricetta, id_ingrediente, id_birra, quantita, nome, quantitaPercentuale)" +
								                   "VALUES ('"+rs1.getInt("id")+"','"+ id_ingr +"','"+id_birra+"','"+Double.valueOf(txtQta.getText())+"','"+nomeRicetta+"', 0)";
									}else {
										sql = "INSERT INTO ricetta (id_ingrediente, id_birra, quantita, nome, quantitaPercentuale)" +
								                   "VALUES ('"+ id_ingr +"','"+id_birra+"','"+Double.valueOf(txtQta.getText())+"','"+nomeRicetta+"', 0)";
									}
									JOptionPane.showMessageDialog(frame, "RICETTA INSERITA");
									rs1.close();
									stmt.executeUpdate(sql);
								} catch (Exception e2) {
									// TODO: handle exception
								}
							}
						}
						else {
							System.out.println("Non vi è nessuna quantità ancora...strano");
						}
						rs.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				initialize(connection, birraio, nomeRicetta, id_birra);
				frame1.dispose();
			}
		});
		frame1.getContentPane().add(btnCreaIngrediente);
	}
	//funzione di supporto per selezione di una ricetta
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void initialize(final Connection connection, final Birraio birraio,final int id_birra, final String operation) { 
		final JFrame frame2 = new JFrame();
		frame2.setVisible(true);
		frame2.setBounds(100, 100, 900, 600);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame2.setJMenuBar(menuBar);
		
		JMenuItem mntmIndietro = new JMenuItem("Indietro");
		mntmIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CRUDoperationsRicetta cRicetta = new CRUDoperationsRicetta(connection, birraio, operation);
				cRicetta.invokeGUI(connection, birraio, operation);
				frame2.dispose();
			}
		});
		menuBar.add(mntmIndietro);
		frame2.getContentPane().setLayout(null);
		
		if(operation.equals("delRic")) {
			ArrayList<String> arrayListRicetta = new ArrayList<String>();
			ricettaList = new ArrayList<Ricetta>();
			try (Statement stmt1 = connection.createStatement();){
				
				//seleziono tutte le birre
				String sql = "SELECT DISTINCT ricetta.id_ricetta AS id, ricetta.nome AS nome FROM birra INNER JOIN birraio ON birra.id_birraio = birraio.id_birraio INNER JOIN ricetta ON ricetta.id_birra = birra.id_birra WHERE birraio.id_birraio = '"+ birraio.getId_birraio() +"' AND birra.id_birra = '"+birraList.get(comboBirra.getSelectedIndex()).getId_birra()+"'";
				try (ResultSet rs = stmt1.executeQuery(sql);){
					int i = 0;
					while(rs.next())
					{
						ricettaList.add(new Ricetta(rs.getInt("id"),0,0,0,rs.getString("nome"),0));
						arrayListRicetta.add(ricettaList.get(i).getNome());
						i++;
					}
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			comboRicetta = new JComboBox(arrayListRicetta.toArray());
			comboRicetta.setBounds(258, 58, 267, 26);
			frame2.getContentPane().add(comboRicetta);
			
			JButton btneliminaRicetta = new JButton("Elimina Ricetta");
			btneliminaRicetta.setBounds(581, 57, 267, 29);
			btneliminaRicetta.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("Deleting ricette into db...");
					
					
					try (Statement stmt1 = connection.createStatement();){
						
						String sql = "DELETE FROM ricetta WHERE id_ricetta = '"+ricettaList.get(comboRicetta.getSelectedIndex()).getId_ricetta()+"' AND id_birra = '"+id_birra+"'";
						stmt1.executeUpdate(sql);
						JOptionPane.showMessageDialog(frame, "RICETTA ELIMINATA");
						CRUDoperationsRicetta cRicetta = new CRUDoperationsRicetta(connection, birraio, "delRic");
						cRicetta.invokeGUI(connection, birraio, "delRic");
						frame2.dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			frame2.getContentPane().add(btneliminaRicetta);
		}
		if(operation.equals("modRic")) {
			ArrayList<String> arrayListRicetta = new ArrayList<String>();
			ricettaList = new ArrayList<Ricetta>();
			try (Statement stmt1 = connection.createStatement();){
				
				//seleziono tutte le ricette
				String sql = "SELECT DISTINCT ricetta.id_ricetta AS id, ricetta.nome AS nome FROM birra INNER JOIN birraio ON birra.id_birraio = birraio.id_birraio INNER JOIN ricetta ON ricetta.id_birra = birra.id_birra WHERE birraio.id_birraio = '"+ birraio.getId_birraio() +"' AND birra.id_birra = '"+birraList.get(comboBirra.getSelectedIndex()).getId_birra()+"'";
				try (ResultSet rs = stmt1.executeQuery(sql);){
					int i = 0;
					while(rs.next())
					{
						ricettaList.add(new Ricetta(rs.getInt("id"),0,0,0,rs.getString("nome"),0));
						arrayListRicetta.add(ricettaList.get(i).getNome());
						i++;
					}
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			comboRicetta = new JComboBox(arrayListRicetta.toArray());
			comboRicetta.setBounds(258, 58, 267, 26);
			frame2.getContentPane().add(comboRicetta);
			
			JButton btnmodificaRicetta = new JButton("Modifica Ricetta");
			btnmodificaRicetta.setBounds(581, 57, 267, 29);
			btnmodificaRicetta.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					initialize(connection, birraio, id_birra, ricettaList.get(comboRicetta.getSelectedIndex()).getId_ricetta(), ricettaList.get(comboRicetta.getSelectedIndex()).getNome(), operation);
					frame2.dispose();
				}
			});
			frame2.getContentPane().add(btnmodificaRicetta);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initialize(final Connection connection, final Birraio birraio,final int id_birra, final int id_ricetta, final String nomeRic, final String operation) {
		final JFrame frame3 = new JFrame();
		frame3.setVisible(true);
		frame3.setBounds(100, 100, 900, 600);
		frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame3.setJMenuBar(menuBar);
		
		JMenuItem mntmIndietro = new JMenuItem("Indietro");
		mntmIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try (Statement stmt = connection.createStatement();){
					//prendo tutti gli ingredienti e le loro quantita inserite in kg
					String sql = "SELECT ricetta.id_ingrediente AS id_ingrediente, ricetta.quantita AS qta, ricetta.id_ricetta AS id_ricetta FROM ricetta WHERE ricetta.id_birra = "+ id_birra +"  AND ricetta.nome = '"+nomeRic+"'";
					try (ResultSet rs = stmt.executeQuery(sql);){
						if(!rs.next())
						{
							JOptionPane.showMessageDialog(frame, "NESSUN INGREDIENTE INSERITO");
						}else 
						{
							ArrayList<Ricetta> ricettas = new ArrayList<Ricetta>();
							rs.beforeFirst();
							double quantitaTot = 0;
							while(rs.next()) {
								ricettas.add(new Ricetta(rs.getInt("id_ricetta"), rs.getDouble("qta"), id_birra, rs.getInt("id_ingrediente"), nomeRic,0));
								quantitaTot += rs.getDouble("qta");
							}
							int i=0;
							while (i<ricettas.size()) {
								ricettas.get(i).setQuantitaPercentuale((ricettas.get(i).getQuantita()*100)/quantitaTot);
								i++;
							}
							i=0;
							while (i<ricettas.size()) {
								Statement stmt1 = connection.createStatement();
								sql = "UPDATE ricetta SET id_ricetta = "+ricettas.get(i).getId_ricetta()+",id_ingrediente = "+ricettas.get(i).getId_ingrediente()+",id_birra = "+ricettas.get(i).getId_birra()+",quantita = "+ricettas.get(i).getQuantita()+",nome = '"+ricettas.get(i).getNome()+"', quantitaPercentuale = "+ricettas.get(i).getQuantitaPercentuale()+" WHERE ricetta.id_ricetta = "+ricettas.get(i).getId_ricetta()+" AND ricetta.id_birra = "+id_birra+" AND ricetta.id_ingrediente = "+ricettas.get(i).getId_ingrediente();
								stmt1.executeUpdate(sql);
								i++;
							}
							
						}
					} catch (Exception e1) {
						// TODO: handle exception
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				CRUDoperationsRicetta cRicetta = new CRUDoperationsRicetta(connection, birraio, "modRic");
				cRicetta.invokeGUI(connection, birraio, "modRic");
				frame3.dispose();
			}
		});
		menuBar.add(mntmIndietro);
		frame3.getContentPane().setLayout(null);
		
		JLabel lblQta = new JLabel("Quantità");
		lblQta.setBounds(77, 128, 182, 20);
		frame3.getContentPane().add(lblQta);

		txtQta = new JTextField();
		txtQta.setBounds(305, 125, 267, 26);
		frame3.getContentPane().add(txtQta);
		txtQta.setColumns(10);
		
		JLabel lblIngrediente = new JLabel("Seleziona Nuovo Ingrediente");
		lblIngrediente.setBounds(77, 67, 182, 20);
		frame3.getContentPane().add(lblIngrediente);
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		ingredienteList = new ArrayList<Ingrediente>();
		try (Statement stmt = connection.createStatement();){
			
			//prendo tutti gli ingredienti che non sono di quella ricetta 
			String sql = "SELECT DISTINCT ingrediente.id_ingrediente AS id, ingrediente.nome AS nome FROM dispensa INNER JOIN ingrediente ON ingrediente.id_ingrediente = dispensa.id_ingrediente WHERE dispensa.id_birraio = '"+ birraio.getId_birraio() +"'  AND ingrediente.id_ingrediente != ALL(SELECT id_ingrediente FROM ricetta WHERE id_ricetta = '"+id_ricetta+"' AND id_birra = '"+id_birra+"')";
			try (ResultSet rs = stmt.executeQuery(sql);){
				int i = 0;
				if(!rs.first())
				{	System.out.println("Non esistono altri ingredienti oltre a quelli già usati!");
				}else {
					rs.beforeFirst();
					while(rs.next())
					{
						ingredienteList.add(new Ingrediente(rs.getInt("id"), rs.getString("nome"), null));
						arrayList.add(ingredienteList.get(i).getNome());
						i++;
					}
					rs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		comboIngrediente = new JComboBox(arrayList.toArray());
		comboIngrediente.setBounds(305, 64, 267, 26);
		frame3.getContentPane().add(comboIngrediente);
		//NUOVO
		if(!arrayList.isEmpty()) {
			JButton btnCreaIngrediente = new JButton("Aggiungi Nuovo Ingrediente");
			btnCreaIngrediente.setBounds(600, 64, 221, 29);
			btnCreaIngrediente.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					try (Statement stmt = connection.createStatement();Statement stmt1 = connection.createStatement();){
						
						//verifica che non vengano superate le quanità massime
						int id_ingr = ingredienteList.get(comboIngrediente.getSelectedIndex()).getId_ingrediente();
						String sql = "SELECT dispensa.qta AS qta FROM dispensa WHERE dispensa.id_ingrediente = '"+ id_ingr +"' AND dispensa.id_birraio = '" + birraio.getId_birraio() + "'";
						try (ResultSet rs = stmt.executeQuery(sql);){
							if(rs.next())
							{
								if(rs.getDouble("qta") < Double.valueOf(txtQta.getText())) {
									JOptionPane.showMessageDialog(frame, "SFORATO LA QUANTITA' MASSIMA");
								}
								else {									
									sql = "INSERT INTO ricetta (id_ricetta, id_ingrediente, id_birra, quantita, nome, quantitaPercentuale)" +
							                   "VALUES ('"+id_ricetta+"','"+ id_ingr +"','"+id_birra+"','"+Double.valueOf(txtQta.getText())+"','"+nomeRic+"', 0)";
									stmt1.executeUpdate(sql);
									JOptionPane.showMessageDialog(frame, "INGREDIENTE INSERITO");
									
								}
							}
							else {
								System.out.println("Non vi è nessuna quantità ancora...strano");
							}
							rs.close();
						} catch (Exception e2) {
							// TODO: handle exception
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					initialize(connection, birraio, id_birra, id_ricetta,nomeRic, operation);
					frame3.dispose();
				}
			});
			frame3.getContentPane().add(btnCreaIngrediente);
		}
		
		JLabel lblOldJLabel = new JLabel("Seleziona Vecchio Ingrediente");
		lblOldJLabel.setBounds(77, 186, 182, 20);
		frame3.getContentPane().add(lblOldJLabel);
		
		ArrayList<String> oldArrayList = new ArrayList<String>();
		final ArrayList<Ingrediente>modificaingredienteList = new ArrayList<Ingrediente>();
		try (Statement stmt = connection.createStatement();){
			//prendo tutti gli ingredienti presenti di quella ricetta
			String sql = "SELECT DISTINCT ingrediente.id_ingrediente AS id, ingrediente.nome AS nome FROM dispensa INNER JOIN ingrediente ON ingrediente.id_ingrediente = dispensa.id_ingrediente WHERE dispensa.id_birraio = '"+ birraio.getId_birraio() +"'  AND ingrediente.id_ingrediente = ANY(SELECT id_ingrediente FROM ricetta WHERE id_ricetta = '"+id_ricetta+"' AND id_birra = '"+id_birra+"')";
			try (ResultSet rs = stmt.executeQuery(sql);){
				int i = 0;
				if(!rs.first())
				{
					System.out.println("Non eistono ancora ingredienti per questa ricetta!");
				}else {
					rs.beforeFirst();
					while(rs.next())
					{
						modificaingredienteList.add(new Ingrediente(rs.getInt("id"), rs.getString("nome"), null));
						oldArrayList.add(modificaingredienteList.get(i).getNome());
						i++;
					}
					rs.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		final JComboBox comboModificaIngrediente = new JComboBox(oldArrayList.toArray());
		comboModificaIngrediente.setBounds(305, 186, 267, 26);
		frame3.getContentPane().add(comboModificaIngrediente);
		
		//VECCHIO => MODIFICA
		if(!oldArrayList.isEmpty())
		{
			JButton btnModificaIngrediente = new JButton("Modifica Vecchio Ingrediente");
			btnModificaIngrediente.setBounds(600, 128, 221, 29);
			btnModificaIngrediente.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					
					try (Statement stmt = connection.createStatement();Statement stmt1 = connection.createStatement();){
						
						//verifica che non vengano superate le quanità massime
						int id_ingr = modificaingredienteList.get(comboModificaIngrediente.getSelectedIndex()).getId_ingrediente();
						String sql = "SELECT dispensa.qta AS qta FROM dispensa WHERE dispensa.id_ingrediente = '"+ id_ingr +"' AND dispensa.id_birraio = '" + birraio.getId_birraio() + "'";
						try (ResultSet rs = stmt.executeQuery(sql);){
							if(rs.next())
							{
								if(rs.getDouble("qta") < Double.valueOf(txtQta.getText())) {
									JOptionPane.showMessageDialog(frame, "SFORATO LA QUANTITA' MASSIMA");
								}
								else {
									System.out.println("Insert modified ingrediente into db...");
									//prendo il nome della ricetta
									sql = "SELECT DISTINCT nome FROM ricetta WHERE id_ricetta = '"+ id_ricetta +"' AND id_birra = '"+id_birra+"'";
									
									try (ResultSet rs1 = stmt1.executeQuery(sql);){
										if(rs1.next())
										{
											sql = "UPDATE ricetta SET ricetta.quantita = '"+Double.parseDouble(txtQta.getText())+"' WHERE ricetta.id_ricetta = '"+id_ricetta+"' AND ricetta.id_birra = '"+id_birra+"' AND ricetta.id_ingrediente = '"+id_ingr+"'";
										}else {
											
										}
										rs1.close();
										stmt.executeUpdate(sql);
									} catch (Exception e2) {
										// TODO: handle exception
									}
									
								}
							}
							else {
								System.out.println("Non vi è nessuna quantità ancora...strano");
							}
							rs.close();
						} catch (Exception e2) {
							// TODO: handle exception
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					initialize(connection, birraio, id_birra, id_ricetta,nomeRic, operation);
					frame3.dispose();
				}
			});
			frame3.getContentPane().add(btnModificaIngrediente);
			
			//VECCHIO => CANCELLA
			JButton btnEliminaIngrediente = new JButton("Elimina Vecchio Ingrediente");
			btnEliminaIngrediente.setBounds(600, 186, 221, 29);
			btnEliminaIngrediente.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					
					try (Statement stmt = connection.createStatement();){
						
						int id_ingr = modificaingredienteList.get(comboModificaIngrediente.getSelectedIndex()).getId_ingrediente();
						String sql = "DELETE FROM ricetta WHERE id_ingrediente = '"+id_ingr+"' AND id_ricetta = '"+id_ricetta+"'";
						stmt.executeUpdate(sql);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					initialize(connection, birraio, id_birra, id_ricetta,nomeRic, operation);
					frame3.dispose();
				}
			});
			frame3.getContentPane().add(btnEliminaIngrediente);
		}
		
		
	}
}
