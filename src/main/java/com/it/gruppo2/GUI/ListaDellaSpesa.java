package com.it.gruppo2.GUI;

import java.awt.BorderLayout;
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
import javax.swing.ListModel;
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
		
		
		
		
		Statement stmt;
		
		try {
			stmt = connection.createStatement();
			String ldsList = new String();
			String sql = "SELECT distinct ingrediente.* FROM dispensa " + 
					"INNER JOIN ingrediente ON dispensa.id_ingrediente = ingrediente.id_ingrediente " + 
					"WHERE dispensa.lds = 'Y' " + 
					"AND dispensa.id_birraio = '" + brewerBirraio.getId_birraio()+ "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				rs.beforeFirst();
				int i=0;
				while(rs.next()==true){
					//inserisco tutte le birre in una lista
					ingredienteList.add(i,new Ingrediente(rs.getInt("id_ingrediente"), rs.getString("nome"), rs.getString("tipo")));
					ingrList1.addElement(ingredienteList.get(i).getNome());
					i++;
				}
				
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
				String code = JOptionPane.showInputDialog(frame, "Aggiornamento dispensa: hai acquistato altro " +listd.getSelectedValue()+"?", "Update lista della spesa", JOptionPane.INFORMATION_MESSAGE);
			    
				if(code != null)
				{
					
					try {
						String sql="UPDATE dispensa SET lds='N', qta = qta + '"+Double.parseDouble(code)+ "' WHERE  id_ingrediente='" +ingredienteList.get(listd.getSelectedIndex()).getId_ingrediente()+"' AND id_birraio='" + brewerBirraio.getId_birraio()+ "'";
						Statement stmt1;
						stmt1 = connection.createStatement();
						stmt1.executeUpdate(sql);
						System.out.println("1");
						BrewDayMenu bDayMenu = new BrewDayMenu(connection, brewerBirraio);
						bDayMenu.invokeGUI(connection, brewerBirraio);
						frame.dispose();
						System.out.println("1");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
