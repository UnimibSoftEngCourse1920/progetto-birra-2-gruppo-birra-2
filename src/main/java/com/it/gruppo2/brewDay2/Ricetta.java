package com.it.gruppo2.brewDay2;

public class Ricetta extends Ingrediente {

	private Double quantita;
	private Integer id_birra, id_ingrediente, id_ricetta;
	private Double quantitaPercentuale;
	private String nome;

	public Ricetta(int id_ricetta, double quantita, int id_birra, int id_ingrediente, String nome, double quantitaPercentuale) {
		super();
		this.quantita = quantita;
		this.id_birra = id_birra;
		this.id_ingrediente = id_ingrediente;
		this.id_ricetta = id_ricetta;
		this.nome = nome;
		this.quantitaPercentuale = quantitaPercentuale;
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

	/**
	 * @return the id_ricetta
	 */
	public Integer getId_ricetta() {
		return id_ricetta;
	}

	/**
	 * @param id_ricetta the id_ricetta to set
	 */
	public void setId_ricetta(int id_ricetta) {
		this.id_ricetta = id_ricetta;
	}

	/**
	 * @param name the name to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the name
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the quantitaPercentuale
	 */
	public Double getQuantitaPercentuale() {
		return quantitaPercentuale;
	}

	/**
	 * @param d the quantitaPercentuale to set
	 */
	public void setQuantitaPercentuale(double d) {
		this.quantitaPercentuale = d;
	}
	
	
	
}



