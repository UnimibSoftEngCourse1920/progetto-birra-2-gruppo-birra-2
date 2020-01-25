package com.it.gruppo2.brewDay2;

public class Ricetta extends Ingrediente {

	private Double quantita;
	private Integer id_birra, id_ingrediente;

	public Ricetta(double quantita, int id_birra, int id_ingrediente) {
		super();
		this.quantita = quantita;
		this.id_birra = id_birra;
		this.id_ingrediente = id_ingrediente;
	}
	
	public Double getQuantita() {
		return quantita;
	}

	public void setQuantitaAssoluta(double quantita) {
		this.quantita = quantita;
	}
	
	public void setQuantitaRelativa(double quantita)		//l'utente deve specificare l'ingrediente e la  
	{
		this.quantita = quantita;
	}

	/**
	 * @return the id_birra
	 */
	public Integer getId_birra() {
		return id_birra;
	}

	/**
	 * @param id_birra the id_birra to set
	 */
	public void setId_birra(int id_birra) {
		this.id_birra = id_birra;
	}

	/**
	 * @return the id_ingrediente
	 */
	public Integer getId_ingrediente() {
		return id_ingrediente;
	}

	/**
	 * @param id_ingrediente the id_ingrediente to set
	 */
	public void setId_ingrediente(int id_ingrediente) {
		this.id_ingrediente = id_ingrediente;
	}


	
	
	
}
