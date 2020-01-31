package com.it.gruppo2.GUI;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.it.gruppo2.brewDay2.Birraio;
import com.it.gruppo2.brewDay2.Ingrediente;

import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaDellaSpesa {

	private JFrame frame;
	private ArrayList<Ingrediente> ingredienteList = new ArrayList<Ingrediente>();
	private DefaultListModel<String> ingrList1 = new DefaultListModel<String>();

	public int invokeGUI(final Connection connection, final Birraio brewerBirraio) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaDellaSpesa window = new ListaDellaSpesa(connection, brewerBirraio);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return 0;
	}
	
	

	/**
	 * Create the application.
	 */
	public ListaDellaSpesa(Connection connection, Birraio brewerBirraio) {
		initialize(connection, brewerBirraio);
	}
	
	private void initialize(final Connection connection, final Birraio brewerBirraio) {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 457);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		try (Statement stmt = connection.createStatement();){
			String sql = "SELECT ingrediente.* FROM dispensa " + 
					"INNER JOIN ingrediente ON dispensa.id_ingrediente = ingrediente.id_ingrediente " + 
					"WHERE dispensa.lds = 'Y' OR dispensa.qta < 1 " + 
					"AND dispensa.id_birraio = " + brewerBirraio.getId_birraio();
			try (ResultSet rs = stmt.executeQuery(sql);){
				if(rs.next())
				{
					rs.beforeFirst();
					int i=0;
					while(rs.next()){
						//inserisco tutte le birre in una lista
						ingredienteList.add(i,new Ingrediente(rs.getInt("id_ingrediente"), rs.getString("nome"), rs.getString("tipo")));
						ingrList1.addElement(ingredienteList.get(i).getNome());
						i++;
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		
		final JList<String> listd = new JList<String>(ingrList1);
		listd.setBounds(50, 74, 200, 200);
		frame.getContentPane().add(listd);
		
		JButton btnTogli = new JButton("Togli dalla lista");
		btnTogli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listd.getSelectedValue()!= null) 
				{
					String code = JOptionPane.showInputDialog(frame, "Aggiornamento dispensa: Quanto/a " +listd.getSelectedValue()+" hai acquistato?", "Update lista della spesa", JOptionPane.INFORMATION_MESSAGE);
					if(!code.isEmpty() && code != null)
					{	
						Double quantitaLitri = 0.0;
						boolean numeric = true;
						try {
							quantitaLitri = Double.parseDouble(code);
				        } catch (NumberFormatException e1) {
				            numeric = false;
				        }
						if(numeric == true && quantitaLitri > 0) {
							try (Statement stmt1 = connection.createStatement();){
								String sql="UPDATE dispensa SET lds='N', qta = qta + '"+Double.parseDouble(code)+ "' WHERE  id_ingrediente='" +ingredienteList.get(listd.getSelectedIndex()).getId_ingrediente()+"' AND id_birraio='" + brewerBirraio.getId_birraio()+ "'";
								stmt1.executeUpdate(sql);
								if(ingredienteList.size() > 1) {
									ListaDellaSpesa listaDellaSpesa = new ListaDellaSpesa(connection, brewerBirraio);
									listaDellaSpesa.invokeGUI(connection, brewerBirraio);
									frame.dispose();
								}else{
									BrewDayMenu bDayMenu = new BrewDayMenu(connection, brewerBirraio);
									bDayMenu.invokeGUI(connection, brewerBirraio);
									frame.dispose();
								}
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(frame, "Inserire dati accettabili");
						}
					}else {
						JOptionPane.showMessageDialog(frame, "Inserire dati accettabili");
					}
				}
			}
		});
		btnTogli.setBounds(300, 74, 151, 31);
		frame.getContentPane().add(btnTogli);
		
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
