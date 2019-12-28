package com.it.gruppo2.brewDay2;

public class Ricetta extends Ingrediente {

	private double quantita;

	public double getQuantita() {
		return quantita;
	}

	public void setQuantitaAssoluta(double quantita) {
		this.quantita = quantita;
	}
	
	public void setQuantitaRelativa(double quantita)		//l'utente deve specificare l'ingrediente e la  
	{
		this.quantita = quantita;
	}
	
	
	
}
