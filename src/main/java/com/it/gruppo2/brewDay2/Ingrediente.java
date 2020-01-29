package com.it.gruppo2.brewDay2;

public class Ingrediente {
	private Integer id_ingrediente;
	private String nome;
	private String tipo;
	
	public Ingrediente(int id_ingrediente,String nome,String tipo) {
		this.id_ingrediente = id_ingrediente;
		this.nome = nome;
		if(tipo.equals("luppolo") || tipo.equals("malto") || tipo.equals("zucchero") || tipo.equals("acqua") || tipo.equals("addittivi") || tipo.equals("lievito"))
			this.tipo = tipo;
	}
	
	public Ingrediente() {
		this.id_ingrediente = null;
		this.tipo = null;
		this.nome = null;
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
	public void setId_ingrediente(Integer id_ingrediente) {
		this.id_ingrediente = id_ingrediente;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		if(tipo== "luppolo" || tipo == "malto" || tipo == "zucchero" || tipo == "acqua" || tipo == "addittivi" || tipo == "lievito")
			this.tipo = tipo;
	};
}
