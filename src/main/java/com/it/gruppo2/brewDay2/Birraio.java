package com.it.gruppo2.brewDay2;

/**
* Classe per rappresentare un birraio
*/
public class Birraio {

	private String nome;
	private String cognome;
	private int id_birraio;
	private String username;
	private String password;
	
	public Birraio(int id_birraio, String nome, String cognome, String username, String password) {
		this.id_birraio = id_birraio;
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the id_birraio
	 */
	public int getId_birraio() {
		return id_birraio;
	}

	/**
	 * @param id_birraio the id_birraio to set
	 */
	public void setId_birraio(int id_birraio) {
		this.id_birraio = id_birraio;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
	
/*
private double amount;
private String owner;
// costruttore
public Conto(String owner, double initialAmount) {
this.owner = owner;
this.amount = initialAmount;
}
public void versamento(double qty) {
amount += qty;
}
public boolean prelievo(double qty) {
if(amount < qty)
return false;
amount -= qty;
return true;
}
public double getAmount() {
return amount;
}
public String getOwner() {
return owner;
}
}*/