package com.it.gruppo2.brewDay2;

public class Birra {

	private String nome;
	private enum tipo {Bionda, Rossa, Nera, Ipa, PaleAle, DoppioMalto, IndianAle};
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
