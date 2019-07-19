package it.betcalculator.domain;

public class Risultato {

	private EsitoEnum esito;
	private Double quota;
	
	public Risultato(EsitoEnum esito, Double quota) {
		super();
		this.esito = esito;
		this.quota = quota;
	}
	public EsitoEnum getEsito() {
		return esito;
	}
	public void setEsito(EsitoEnum esito) {
		this.esito = esito;
	}
	public Double getQuota() {
		return quota;
	}
	public void setQuota(Double quota) {
		this.quota = quota;
	}
	
	
	
}
