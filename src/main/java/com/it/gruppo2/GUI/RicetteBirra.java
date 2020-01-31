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
import com.it.gruppo2.brewDay2.Birraio;
import com.it.gruppo2.brewDay2.Ingrediente;
import com.it.gruppo2.brewDay2.Ricetta;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class RicetteBirra {

	private JFrame frame;
	private ArrayList<Ricetta> ricettArrayList = new ArrayList<Ricetta>();
	private ArrayList<Ingrediente> ingredienteArrayList = new ArrayList<Ingrediente>();
	private int capienzaAttr = 0;
	
	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection,final Birra birra, final Birraio brewerBirraio) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RicetteBirra window = new RicetteBirra(connection, birra, brewerBirraio);
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
	public RicetteBirra(Connection connection, Birra birra, Birraio brewerBirraio) {
		initialize(connection, birra, brewerBirraio);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Connection connection,final Birra birra, final Birraio brewerBirraio) {
		frame = new JFrame();
		frame.setBounds(100, 100, 899, 453);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		try (Statement stmt2 = connection.createStatement();Statement stmt4 = connection.createStatement()){
			
    		ResultSet rs1,rs3;
    		DefaultListModel<String> ricetteListModel = new DefaultListModel<String>();
    		DefaultListModel<String> ingredienteListModel = new DefaultListModel<String>();
    		DefaultListModel<String> weightListModel = new DefaultListModel<String>();
    		DefaultListModel<String> percentualeListModel = new DefaultListModel<String>();
            
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
					ricettArrayList.add(j,new Ricetta(rs1.getInt("id_ricetta"),rs3.getDouble("quantita"),(int)birra.getId_birra(),rs3.getInt("id_ingrediente"), rs1.getString("nome"), rs3.getDouble("quantitaPercentuale")));
					
    				ingredienteArrayList.add(j,new Ingrediente(rs3.getInt("id_ingrediente"),rs3.getString("nome"),rs3.getString("tipo")));
    				ingredienteListModel.addElement(ingredienteArrayList.get(j).getNome());
    				
    				weightListModel.addElement(ricettArrayList.get(j).getQuantita().toString());
    				percentualeListModel.addElement(ricettArrayList.get(j).getQuantitaPercentuale().toString());
    				j++;
	    		}
	    		ricetteListModel.addElement(rs1.getString("nome"));
	    		ingredienteListModel.addElement("fine ingredienti di "+ rs1.getString("nome"));
	    		weightListModel.addElement("fine pesi di "+ rs1.getString("nome"));
	    		percentualeListModel.addElement("fine percentuali di "+ rs1.getString("nome"));
	    		
	    		rs3.close();
    		}
    		rs1.close();
    		frame.getContentPane().setLayout(null);
    		final JList<String> listRicette = new JList<String>(ricetteListModel);
    		listRicette.setValueIsAdjusting(true);
    		listRicette.setBounds(15, 100, 200, 200);
    		frame.getContentPane().add(listRicette);
    		
    		JList<String> listIngrediente = new JList<String>(ingredienteListModel);
    		listIngrediente.setValueIsAdjusting(true);
    		listIngrediente.setBounds(230, 100, 200, 200);
    		frame.getContentPane().add(listIngrediente);
    		
			JList<String> listWeight = new JList<String>(weightListModel);
    		listWeight.setValueIsAdjusting(true);
    		listWeight.setBounds(445, 100, 200, 200);
    		frame.getContentPane().add(listWeight);
    		
    		JList<String> listPercentuali = new JList<String>(percentualeListModel);
    		listPercentuali.setValueIsAdjusting(true);
    		listPercentuali.setBounds(660, 100, 200, 200);
    		frame.getContentPane().add(listPercentuali);
    		
    		JMenuBar menuBar = new JMenuBar();
    		menuBar.setBounds(0, 0, 877, 31);
    		frame.getContentPane().add(menuBar);
    		
    		JMenuItem plsBack = new JMenuItem("Indietro");
    		plsBack.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mousePressed(MouseEvent arg0) {
    				BrewDayMenu brewDayMenu = new BrewDayMenu(connection, brewerBirraio);
    				brewDayMenu.invokeGUI(connection, brewerBirraio);
    				frame.dispose();
    			}
    		});
    		menuBar.add(plsBack);
    		
    		JButton btnProduciRicetta = new JButton("Produci RIcetta");
    		btnProduciRicetta.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mousePressed(MouseEvent arg0) {
    				String nomeRicetta = listRicette.getSelectedValue();
    				if(nomeRicetta == null)
    					JOptionPane.showMessageDialog(frame, "Non hai selezionato nessuna ricetta");
    				else {
    					
    					String qtaString = JOptionPane.showInputDialog(frame, "Quanti litri vuoi produrre di birra?", "Inserimento quantità");
    					
    					if(!qtaString.isEmpty() && qtaString != null)
    					{	
    						Double quantitaLitri = 0.0;
    						boolean numeric = true;
    						try {
    							quantitaLitri = Double.parseDouble(qtaString);
    				        } catch (NumberFormatException e) {
    				            numeric = false;
    				        }
    						if(numeric == true && quantitaLitri > 0) {
        						try (Statement stmtStatement = connection.createStatement()){
        							//prendo la capacità massima dell'attrazzatura del birraio 
        							String sql = "SELECT attrezzatura.capacita FROM attrezzatura WHERE disponibilita = 'Y' AND id_birraio= "+brewerBirraio.getId_birraio()+" ORDER BY capacita DESC LIMIT 1";
        							ResultSet rs = stmtStatement.executeQuery(sql);
        							if(rs.next()) {
        								capienzaAttr=rs.getInt("capacita");
        							}
        							rs.close();
        							//vengono selezionati tutti gli ingredienti della ricetta selezioata 
        							sql = "SELECT * FROM ricetta WHERE nome = '"+nomeRicetta+"' AND id_birra = "+birra.getId_birra();
        							try (ResultSet rSet = stmtStatement.executeQuery(sql);){
        								//passo al setaccio tutti gli ingredienti
										int i = 0;Double quantitaFinale,quantitaTotFinale =0.0;boolean conntrolloDispensa = true;
										ArrayList<Ricetta> ingrRicettaFinale = new ArrayList<Ricetta>();
        								while(rSet.next()) {
        									quantitaFinale = (rSet.getDouble("quantitaPercentuale")/100)*quantitaLitri;
        									quantitaTotFinale += quantitaFinale;
        									//preso tutti gli ingredienti che devono essere utlizzati con la qta corretta
    										ingrRicettaFinale.add(i, new Ricetta(rSet.getInt("id_ricetta"), quantitaFinale, birra.getId_birra(),rSet.getInt("id_ingrediente"),rSet.getString("nome"),0));
    										sql = "SELECT qta FROM dispensa WHERE id_birraio = "+brewerBirraio.getId_birraio()+" AND lds = 'N' AND id_ingrediente = "+ingrRicettaFinale.get(i).getId_ingrediente();
        									try (Statement stmt5 = connection.createStatement();){
        										try (ResultSet resultSet = stmt5.executeQuery(sql)){
    												if(resultSet.next()) {
    													if(resultSet.getDouble("qta")<ingrRicettaFinale.get(i).getQuantita())
    														{
    														conntrolloDispensa = false;
    														JOptionPane.showMessageDialog(frame, "Hai un ingrediente che non ha abbastnza litri in dispensa. Tale: "+ingrRicettaFinale.get(i).getId_ingrediente());
    														}
    													else {
    														System.out.print("Tale ingrediente va bene "+ ingrRicettaFinale.get(i).getId_ingrediente());
    													}
    												}
    											} catch (Exception e) {
    												// TODO: handle exception
    											}
											} catch (Exception e) {
												// TODO: handle exception
											}
    										i++;
										}
        								//faccio il controllo se la ricetta va bene
        								if(quantitaTotFinale<capienzaAttr && conntrolloDispensa == true) {
        									JOptionPane.showMessageDialog(frame, "Procediamo a produrre");
        									//aggiornare le quantità tutti gli ingredienti di quella ricetta
        									int h = 0;
        									while(h<ingrRicettaFinale.size()) {
        											try (Statement stmt4 = connection.createStatement();){
        												sql="UPDATE dispensa SET qta = (qta - "+ingrRicettaFinale.get(h).getQuantita()+") WHERE id_ingrediente= "+ingrRicettaFinale.get(h).getId_ingrediente()+" AND id_birraio = "+brewerBirraio.getId_birraio();                 
        												stmt4.executeUpdate(sql);
        											} catch (Exception e2) {
        												// TODO: handle exception
        											}
        										h++;
        									}
        									BrewDayMenu bDayMenu = new BrewDayMenu(connection, brewerBirraio);
        									bDayMenu.invokeGUI(connection, brewerBirraio, 1);
        									frame.dispose();
        								}
									} catch (Exception e) {
										// TODO: handle exception
									}
								} catch (Exception e) {
									// TODO: handle exception
								}
        					}else {
        						JOptionPane.showMessageDialog(frame, "Inserire dati accettabili");
    						}
    					}
    					else {
    						JOptionPane.showMessageDialog(frame, "Inserire dati accettabili");
    					}
    				}
    			}
    		});
    		btnProduciRicetta.setBounds(660, 333, 200, 29);
    		frame.getContentPane().add(btnProduciRicetta);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
