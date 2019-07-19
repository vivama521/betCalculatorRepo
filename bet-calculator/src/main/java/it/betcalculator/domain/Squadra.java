package it.betcalculator.domain;

public class Squadra {
	
	private long idSquadra;
	private String nome;
	
	public Squadra(String nome) {
		super();
		this.nome = nome;
	}
	
	public long getIdSquadra() {
		return idSquadra;
	}

	public void setIdSquadra(long idSquadra) {
		this.idSquadra = idSquadra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
