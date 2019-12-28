package com.it.gruppo2.brewDay2;

/**
* Classe per rappresentare un birraio
*/
public class Birraio {

	private String nome;
	private String cognome;
	

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