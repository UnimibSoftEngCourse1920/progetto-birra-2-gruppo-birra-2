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

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	private ArrayList<Birra> birraList;
	private ArrayList<Ingrediente> ingredienteList;
	
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
	private void initialize(final Connection connection, final Birraio birraio, String operation) throws SQLException {
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
		if(operation == "newRic") {
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
			Statement stmt;
			birraList = new ArrayList<Birra>();
			try {
				stmt = connection.createStatement();
				//verifica che non esista un altro ingrediente uguale
				String sql = "SELECT birra.id_birra AS id, birra.nome AS nome FROM birra INNER JOIN birraio ON birra.id_birraio = birraio.id_birraio WHERE birraio.id_birraio = '"+ birraio.getId_birraio() +"'";
				ResultSet rs = stmt.executeQuery(sql);
				int i = 0;
				while(rs.next())
				{
					birraList.add(new Birra(rs.getInt("id"), rs.getString("nome"), null, 0));
					arrayList.add(birraList.get(i).getNome());
					i++;
				}
				rs.close();
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
			
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize(final Connection connection, final Birraio birraio, final String nomeRicetta, final int id_birra) {
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
				CRUDoperationsRicetta cRicetta = new CRUDoperationsRicetta(connection, birraio, "newRic");
				cRicetta.invokeGUI(connection, birraio, "newRic");
				frame1.dispose();
			}
		});
		menuBar.add(mntmIndietro);
		frame1.getContentPane().setLayout(null);
		
		JLabel lblQta = new JLabel("Quantità");
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
		Statement stmt;
		ingredienteList = new ArrayList<Ingrediente>();
		try {
			stmt = connection.createStatement();
			//prendo tutti gli ingredienti
			String sql = "SELECT ingrediente.id_ingrediente AS id, ingrediente.nome AS nome FROM dispensa INNER JOIN ingrediente ON ingrediente.id_ingrediente = dispensa.id_ingrediente WHERE dispensa.id_birraio = '"+ birraio.getId_birraio() +"'  AND ingrediente.id_ingrediente != ALL(SELECT id_ingrediente FROM ricetta WHERE nome = '"+nomeRicetta+"' AND id_birra = '"+id_birra+"')";
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			if(!rs.first())
			{
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
				Statement stmt;
				try {
					stmt = connection.createStatement();
					//verifica che non vengano superate le quanità massime
					int id_ingr = ingredienteList.get(comboIngrediente.getSelectedIndex()).getId_ingrediente();
					System.out.println(Double.valueOf(txtQta.getText()));
					String sql = "SELECT dispensa.qta AS qta FROM dispensa WHERE dispensa.id_ingrediente = '"+ id_ingr +"' AND dispensa.id_birraio = '" + birraio.getId_birraio() + "'";
					ResultSet rs = stmt.executeQuery(sql);

					if(rs.next())
					{
						if(rs.getDouble("qta") < Double.valueOf(txtQta.getText())) {
							System.out.println("SFORATO LA QUANTITA' MASSIMA!");
						}
						else {
						System.out.println("Insert new ingrediente into db...");
						//verifico se la ricetta esiste già
						sql = "SELECT DISTINCT id_ricetta AS id FROM ricetta WHERE nome = '"+ nomeRicetta +"' AND id_birra = '"+id_birra+"'";
						Statement stmt1 = connection.createStatement();
						ResultSet rs1 = stmt1.executeQuery(sql);
						if(rs1.next())
						{
							sql = "INSERT INTO ricetta (id_ricetta, id_ingrediente, id_birra, quantita, nome)" +
					                   "VALUES ('"+rs1.getInt("id")+"','"+ id_ingr +"','"+id_birra+"','"+Double.valueOf(txtQta.getText())+"','"+nomeRicetta+"')";
						}else {
							sql = "INSERT INTO ricetta (id_ingrediente, id_birra, quantita, nome)" +
					                   "VALUES ('"+ id_ingr +"','"+id_birra+"','"+Double.valueOf(txtQta.getText())+"','"+nomeRicetta+"')";
						}
						rs1.close();
						stmt.executeUpdate(sql);
						}
					}
					else {
						System.out.println("Non vi è nessuna quantità ancora...strano");
					}
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				initialize(connection, birraio, nomeRicetta, id_birra);
				frame1.dispose();
			}
		});
		frame1.getContentPane().add(btnCreaIngrediente);
	}
}
