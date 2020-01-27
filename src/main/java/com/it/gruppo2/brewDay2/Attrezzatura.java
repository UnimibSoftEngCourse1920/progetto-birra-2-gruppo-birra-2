package com.it.gruppo2.brewDay2;

public class Attrezzatura {

	private String nome;
	private Integer capacita;
	private Integer id_attrezzatura;
	private Integer id_birraio;
	
	public Attrezzatura(Integer id_attrezzatura, String nome, Integer capacita, Integer id_birraio) {
		super();
		this.nome = nome;
		this.capacita = capacita;
		this.id_attrezzatura=id_attrezzatura;
		this.setId_birraio(id_birraio);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getCapacita() {
		return capacita;
	}
	public void setCapacita(int capacita) {
		this.capacita = capacita;
	}

	public Integer getId_attrezzatura() {
		return id_attrezzatura;
	}

	public void setId_attrezzatura(int id_attrezzatura) {
		this.id_attrezzatura = id_attrezzatura;
	}

	public Integer getId_birraio() {
		return id_birraio;
	}

	public void setId_birraio(int id_birraio) {
		this.id_birraio = id_birraio;
	}
	
}
