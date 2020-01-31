package com.it.gruppo2.GUI;

import java.awt.EventQueue; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.it.gruppo2.brewDay2.Attrezzatura;
import com.it.gruppo2.brewDay2.Birraio;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class CRUDoperationsAttrezzatura {

	private JFrame frame;
	private JTextField txtNome;
	private JTextField txtCapacita;
	JComboBox<String> comboBox = new JComboBox<String>();
	final JList<String> listAttrName = new JList<String>();
	final JList<Double> listCapienza = new JList<Double>();

	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection, final Birraio birraio, final String operation) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUDoperationsAttrezzatura window = new CRUDoperationsAttrezzatura(connection, birraio, operation);
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
	public CRUDoperationsAttrezzatura(Connection connection, Birraio birraio, String operation) {
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
		
		//ELIMINA ATTREZZATURA
		if(operation.equals("delAttr")) {
			JLabel lblNome = new JLabel("Nome attrezzatura");
			lblNome.setBounds(61, 75, 150, 20);
			frame.getContentPane().add(lblNome);
			
			ArrayList<String> arrayListAttrezzatura = new ArrayList<String>();
			final ArrayList<Attrezzatura> attrezzaturaList = new ArrayList<Attrezzatura>();
			//seleziono tutti gli ingredienti
			Statement stmt1 = connection.createStatement();
			String sql = "SELECT attrezzatura.id_attrezzatura AS id, attrezzatura.nome AS nome FROM attrezzatura INNER JOIN birraio ON birraio.id_birraio = attrezzatura.id_birraio WHERE attrezzatura.id_birraio = '"+birraio.getId_birraio()+"'";
			try (ResultSet rs = stmt1.executeQuery(sql);){
				int i = 0;
				while(rs.next())
				{
					attrezzaturaList.add(i, new Attrezzatura(rs.getInt("id"), rs.getString("nome"), null, null));
					arrayListAttrezzatura.add(attrezzaturaList.get(i).getNome());
					System.out.println(arrayListAttrezzatura.get(i));
					i++;
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			final JComboBox comboAttrezzatura = new JComboBox(arrayListAttrezzatura.toArray());
			comboAttrezzatura.setBounds(258, 75, 267, 26);
			frame.getContentPane().add(comboAttrezzatura);
			
			

			
			final JButton btnEliminaAttrezzatura = new JButton("Elimina Attrezzatura");
			btnEliminaAttrezzatura.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int id_attr = attrezzaturaList.get(comboAttrezzatura.getSelectedIndex()).getId_attrezzatura();
					System.out.println(id_attr);
					try (Statement stmtStatement = connection.createStatement();){
						String sql = "DELETE FROM attrezzatura WHERE id_attrezzatura = '"+id_attr+"'";
						stmtStatement.executeUpdate(sql);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
					bDayMenu.invokeGUI(connection, birraio);
					frame.dispose();
				}
			});
			btnEliminaAttrezzatura.setBounds(543, 75, 150, 29);
			frame.getContentPane().add(btnEliminaAttrezzatura);
		}
		
		//Nuova Attrezzatura
		if(operation.equals("addAttr")) {
			JLabel lblNomeAttrezzatura = new JLabel("Nome attrezzatura");
			lblNomeAttrezzatura.setBounds(77, 64, 182, 20);
			frame.getContentPane().add(lblNomeAttrezzatura);
			
			JLabel lblQuantita = new JLabel("Quantità Attrezzatura");
			lblQuantita.setBounds(77, 196, 182, 20);
			frame.getContentPane().add(lblQuantita);
			
			txtNome = new JTextField();
			txtNome.setBounds(305, 64, 267, 26);
			frame.getContentPane().add(txtNome);
			txtNome.setColumns(10);
			
			txtCapacita = new JTextField();
			txtCapacita.setBounds(305, 196, 267, 26);
			frame.getContentPane().add(txtCapacita);
			txtCapacita.setColumns(10);
			
			
			
			JButton btnCreaAttrezzatura = new JButton("Crea attrezzatura");
			btnCreaAttrezzatura.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					try (Statement stmt = connection.createStatement();){
						
						System.out.println("Insert quantity into db...");
						if(Integer.parseInt(txtCapacita.getText()) <= 0)
							JOptionPane.showMessageDialog(frame, "Inserisci un numero maggiore di 0");
						else {
							
							String sql = "INSERT INTO attrezzatura (nome, capacita, id_birraio)" +
					                   "VALUES ('"+txtNome.getText()+"', '"+Integer.parseInt(txtCapacita.getText())+"','"+birraio.getId_birraio()+"')";
							stmt.executeUpdate(sql);
							BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
							bDayMenu.invokeGUI(connection, birraio);
							frame.dispose();
						}
						
						
					
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			});
			btnCreaAttrezzatura.setBounds(600, 124, 192, 29);
			frame.getContentPane().add(btnCreaAttrezzatura);
		}
		
		//Modifica Attrezzatura
		if(operation.equals("updAttr")) {
			JLabel lblNomeAttrezzatura = new JLabel("Nome attrezzatura");
			lblNomeAttrezzatura.setBounds(77, 64, 182, 20);
			frame.getContentPane().add(lblNomeAttrezzatura);
			
			txtNome = new JTextField();
			txtNome.setBounds(305, 61, 267, 26);
			frame.getContentPane().add(txtNome);
			txtNome.setColumns(10);
			
			txtCapacita = new JTextField();
			txtCapacita.setBounds(305, 196, 267, 26);
			frame.getContentPane().add(txtCapacita);
			txtCapacita.setColumns(10);
			
			JLabel lblCapacita = new JLabel("Capacità");
			lblCapacita.setBounds(77, 199, 69, 20);
			frame.getContentPane().add(lblCapacita);
			
			JButton btnCreaAttrezzatura = new JButton("Crea attrezzatura");
			btnCreaAttrezzatura.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					try (Statement stmt = connection.createStatement();){
						
						System.out.println("Insert quantity into db...");
						if(Integer.parseInt(txtCapacita.getText()) <= 0)
							JOptionPane.showMessageDialog(frame, "Inserisci un numero maggiore di 0");
						else {
							
							String sql = "INSERT INTO attrezzatura (nome, capacita, id_birraio)" +
					                   "VALUES ('"+txtNome.getText()+"', '"+Integer.parseInt(txtCapacita.getText())+"','"+birraio.getId_birraio()+"')";
							stmt.executeUpdate(sql);
							BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
							bDayMenu.invokeGUI(connection, birraio);
							frame.dispose();
						}
					
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			});
			btnCreaAttrezzatura.setBounds(600, 124, 192, 29);
			frame.getContentPane().add(btnCreaAttrezzatura);
		}
		
		//MOSTRA ATTREZZATURA
		if(operation.equals("showAttr")) 
		{	
			try (Statement stmt = connection.createStatement();){
				String sql = "SELECT attrezzatura.nome AS nome, attrezzatura.capacita AS capacita FROM attrezzatura WHERE attrezzatura.id_birraio = '" + birraio.getId_birraio() + "'";
				try (ResultSet rs = stmt.executeQuery(sql);){
					DefaultListModel<String> nomeList = new DefaultListModel<String>();
					DefaultListModel<String> capacitaList = new DefaultListModel<String>();
					int o=0;
					while(rs.next())
					{
							nomeList.add(o, rs.getString("nome"));
							capacitaList.add(o, Double.toString(rs.getDouble("capacita")));
							o++;
					}
					
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setBounds(64, 32, 200, 200);
					frame.getContentPane().add(scrollPane);
					
					final JList<String> listAttrName_1 = new JList<String>(nomeList);
					scrollPane.setViewportView(listAttrName_1);
					
					JScrollPane scrollPane_1 = new JScrollPane();
					scrollPane_1.setBounds(300, 32, 200, 200);
					frame.getContentPane().add(scrollPane_1);
					
					final JList<String> listAttrQta = new JList<String>(capacitaList);
					scrollPane_1.setViewportView(listAttrQta);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}
		}
	}
}
