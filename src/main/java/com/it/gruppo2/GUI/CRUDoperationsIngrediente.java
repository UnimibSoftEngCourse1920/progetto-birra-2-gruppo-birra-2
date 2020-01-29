package com.it.gruppo2.GUI;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.it.gruppo2.brewDay2.Birraio;
import com.it.gruppo2.brewDay2.Ingrediente;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class CRUDoperationsIngrediente {

	private JFrame frame;
	private JTextField txtNome;
	private JTextField txtQta;
	JComboBox<String> comboBox = new JComboBox<String>();

	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection, final Birraio birraio, final String operation) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUDoperationsIngrediente window = new CRUDoperationsIngrediente(connection, birraio, operation);
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
	public CRUDoperationsIngrediente(Connection connection, Birraio birraio, String operation) {
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		
		if(operation.equals("delIngr")) {
			JLabel lblNome = new JLabel("Nome Ingrediente");
			lblNome.setBounds(61, 75, 69, 20);
			frame.getContentPane().add(lblNome);
			
			ArrayList<String> arrayListIngrediente = new ArrayList<String>();
			final ArrayList<Ingrediente> ingredienteList = new ArrayList<Ingrediente>();
			try {
				Statement stmt1 = connection.createStatement();
				//seleziono tutti gli ingredienti
				String sql = "SELECT ingrediente.id_ingrediente AS id, ingrediente.nome FROM ingrediente INNER JOIN dispensa ON dispensa.id_ingrediente = ingrediente.id_ingrediente WHERE dispensa.id_birraio = '"+birraio.getId_birraio()+"'";
				ResultSet rs = stmt1.executeQuery(sql);
				int i = 0;
				while(rs.next())
				{
					ingredienteList.add(new Ingrediente(rs.getInt("id"),rs.getString("nome"), null));
					arrayListIngrediente.add(ingredienteList.get(i).getNome());
					i++;
				}
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			final JComboBox comboIngrediente = new JComboBox(arrayListIngrediente.toArray());
			comboIngrediente.setBounds(258, 75, 267, 26);
			frame.getContentPane().add(comboIngrediente);
			
			JButton btnEliminaIngrediente = new JButton("Elimina Ingrediente");
			btnEliminaIngrediente.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int id_ingr = ingredienteList.get(comboIngrediente.getSelectedIndex()).getId_ingrediente();
					try {
						String sql = "DELETE FROM dispensa WHERE id_ingrediente = '"+id_ingr+"'";
						Statement stmtStatement = connection.createStatement();
						stmtStatement.executeUpdate(sql);
						sql = "DELETE FROM ricetta WHERE id_ingrediente = '"+id_ingr+"'";
						stmtStatement.executeUpdate(sql);
						sql = "DELETE FROM ingrediente WHERE id_ingrediente = '"+id_ingr+"'";
						stmtStatement.executeUpdate(sql);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
					bDayMenu.invokeGUI(connection, birraio);
					frame.dispose();
				}
			});
			btnEliminaIngrediente.setBounds(543, 75, 115, 29);
			frame.getContentPane().add(btnEliminaIngrediente);
		}
		if(operation.equals("addIngr")) {
			JLabel lblNomeIngrediente = new JLabel("Nome Ingrediente");
			lblNomeIngrediente.setBounds(77, 64, 182, 20);
			frame.getContentPane().add(lblNomeIngrediente);
			
			txtNome = new JTextField();
			txtNome.setBounds(305, 61, 267, 26);
			frame.getContentPane().add(txtNome);
			txtNome.setColumns(10);
			
			JLabel lblTipo = new JLabel("Tipo");
			lblTipo.setBounds(77, 128, 182, 20);
			frame.getContentPane().add(lblTipo);
			
			String[] tipo = {"acqua", "malto", "luppolo", "lievito", "addittivi","zucchero"};
			comboBox = new JComboBox<String>(tipo);
			comboBox.setSelectedIndex(1);
			comboBox.setBounds(305, 125, 267, 26);
			frame.getContentPane().add(comboBox);
			
			txtQta = new JTextField();
			txtQta.setBounds(305, 196, 267, 26);
			frame.getContentPane().add(txtQta);
			txtQta.setColumns(10);
			
			JLabel lblQuantit = new JLabel("Quantità");
			lblQuantit.setBounds(77, 199, 69, 20);
			frame.getContentPane().add(lblQuantit);
			
			JButton btnCreaIngrediente = new JButton("Crea Ingrediente");
			btnCreaIngrediente.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					Statement stmt;
					try {
						stmt = connection.createStatement();
						//verifica che non esista un altro ingrediente uguale
						String sql = "SELECT ingrediente.nome AS nome FROM dispensa INNER JOIN ingrediente ON ingrediente.id_ingrediente = dispensa.id_ingrediente WHERE ingrediente.nome = '"+ txtNome.getText() +"' AND ingrediente.tipo = '"+ comboBox.getSelectedItem() +"' AND dispensa.id_birraio = '" + birraio.getId_birraio() + "'";
						ResultSet rs = stmt.executeQuery(sql);

						if(rs.next())
						{
							System.out.println("ERROR: ALREADY EXISTS!");	
						}
						else {
							System.out.println("Insert new ingrediente into db...");
							sql = "INSERT INTO ingrediente (nome,tipo)" +
					                   "VALUES ('"+txtNome.getText()+"','"+comboBox.getSelectedItem()+"')";
							stmt.executeUpdate(sql);
							sql = "SELECT MAX(ingrediente.id_ingrediente) AS id FROM ingrediente";
							rs = stmt.executeQuery(sql);
							rs.next();
							System.out.println("Insert quantity into db...");
							sql = "INSERT INTO dispensa (qta, id_ingrediente, id_birraio)" +
					                   "VALUES ('"+txtQta.getText()+"','"+rs.getInt("id")+"','"+birraio.getId_birraio()+"')";
							stmt.executeUpdate(sql);
							BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
							bDayMenu.invokeGUI(connection, birraio);
							frame.dispose();
						}
						rs.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			btnCreaIngrediente.setBounds(600, 124, 192, 29);
			frame.getContentPane().add(btnCreaIngrediente);
		}
		if(operation.equals("showIngr")) 
		{
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
