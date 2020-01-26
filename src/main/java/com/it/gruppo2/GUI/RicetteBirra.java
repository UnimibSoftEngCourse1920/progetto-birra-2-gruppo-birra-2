package com.it.gruppo2.GUI;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;

import com.it.gruppo2.brewDay2.Birraio;

public class RicetteBirra {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void invokeGUI(Connection connection, int index, Birraio brewerBirraio) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RicetteBirra window = new RicetteBirra();
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
	public RicetteBirra() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
