package com.it.gruppo2.GUI;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.it.gruppo2.brewDay2.Birra;
import com.it.gruppo2.brewDay2.Birraio;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class CRUDoperationsBirra {

	private JFrame frame;
	private JTextField textNome;

	/**
	 * Launch the application.
	 */
	public void invokeGUI(final Connection connection, final Birraio birraio, final String operation) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUDoperationsBirra window = new CRUDoperationsBirra(connection, birraio, operation);
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
	public CRUDoperationsBirra(Connection connection, Birraio birraio, String operation) {
		initialize(connection, birraio, operation);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize(final Connection connection, final Birraio birraio, String operation) {
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
		
		if(operation.equals("delBir")) {
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(61, 75, 69, 20);
			frame.getContentPane().add(lblNome);
			
			ArrayList<String> arrayListBirra = new ArrayList<String>();
			final ArrayList<Birra> birraList = new ArrayList<Birra>();
			try(Statement stmt1 = connection.createStatement();) {
				//seleziono tutte le birre
				String sql = "SELECT id_birra AS id, nome FROM birra WHERE id_birraio = '"+birraio.getId_birraio()+"'";
				try(ResultSet rs = stmt1.executeQuery(sql)) {
					int i = 0;
					while(rs.next())
					{
						birraList.add(new Birra(rs.getInt("id"),rs.getString("nome"), null, 0));
						arrayListBirra.add(birraList.get(i).getNome());
						i++;
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			final JComboBox comboBirra = new JComboBox(arrayListBirra.toArray());
			comboBirra.setBounds(258, 75, 267, 26);
			frame.getContentPane().add(comboBirra);
			
			JButton btnEliminaBirra = new JButton("Elimina Birra");
			btnEliminaBirra.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					int id_birra = birraList.get(comboBirra.getSelectedIndex()).getId_birra();
					try(Statement stmtStatement = connection.createStatement()) {
						String sql = "DELETE FROM ricetta WHERE id_birra = '"+id_birra+"'";
						stmtStatement.executeUpdate(sql);
						sql = "DELETE FROM birra WHERE id_birra = '"+id_birra+"' AND id_birraio = '"+birraio.getId_birraio()+"'";
						stmtStatement.executeUpdate(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
					bDayMenu.invokeGUI(connection, birraio);
					frame.dispose();
				}
			});
			btnEliminaBirra.setBounds(543, 75, 115, 29);
			frame.getContentPane().add(btnEliminaBirra);
		}
		if(operation.equals("aggBir")) {
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(61, 79, 69, 20);
			frame.getContentPane().add(lblNome);
			
			textNome = new JTextField();
			textNome.setBounds(181, 76, 289, 26);
			frame.getContentPane().add(textNome);
			textNome.setColumns(10);
			
			JLabel lblNote = new JLabel("Note:");
			lblNote.setBounds(61, 154, 69, 20);
			frame.getContentPane().add(lblNote);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(181, 154, 289, 341);
			frame.getContentPane().add(scrollPane);
			
			final JTextArea txtNote = new JTextArea();
			scrollPane.setViewportView(txtNote);
			
			JButton btnCreaBirra = new JButton("Crea Birra");
			btnCreaBirra.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					try (Statement stmtStatement = connection.createStatement()){
						String sql = "SELECT id_birra FROM birra INNER JOIN birraio ON birraio.id_birraio = birra.id_birraio WHERE birraio.id_birraio = '"+birraio.getId_birraio()+"' AND birra.nome = '"+textNome.getText()+"'";
						try(ResultSet rSet = stmtStatement.executeQuery(sql);) {
							if(rSet.next()) {
								System.out.println("Esiste già tale birra con questo nome!");
							}else {
								sql = "INSERT INTO birra (nome, note, id_birraio) VALUES ('"+textNome.getText()+"','"+txtNote.getText()+"',"+birraio.getId_birraio()+")";
								stmtStatement.executeUpdate(sql);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					BrewDayMenu bDayMenu = new BrewDayMenu(connection, birraio);
					bDayMenu.invokeGUI(connection, birraio);
					frame.dispose();
				}
			});
			btnCreaBirra.setBounds(543, 75, 115, 29);
			frame.getContentPane().add(btnCreaBirra);
			
		}
	}
}
