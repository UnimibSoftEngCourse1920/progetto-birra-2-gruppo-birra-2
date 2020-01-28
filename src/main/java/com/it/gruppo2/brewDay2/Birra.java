package com.it.gruppo2.brewDay2;

public class Birra {

	private String nome, note;
	private Integer id_birraio, id_birra;

	public Birra(int id_birra, String nome, String note, int id_birraio) {
		this.id_birra = id_birra;
		this.nome = nome;
		this.note = note;
		this.id_birraio = id_birraio;
	}
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setnote(String note) {
		this.note = note;
	}
	/**
	 * @return the id_birraio
	 */
	public Integer getId_birraio() {
		return id_birraio;
	}
	/**
	 * @param id_birraio the id_birraio to set
	 */
	public void setId_birraio(Integer id_birraio) {
		this.id_birraio = id_birraio;
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
	public void setId_birra(Integer id_birra) {
		this.id_birra = id_birra;
	}
	

}
