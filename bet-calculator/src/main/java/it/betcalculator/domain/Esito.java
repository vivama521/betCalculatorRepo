package it.betcalculator.domain;

public class Esito {
	
	private long idEsito;
	private EsitoEnum esitoEnum;
	private double quota;
	
	public Esito(EsitoEnum esitoEnum, double quota) {
		super();
		this.esitoEnum = esitoEnum;
		this.quota = quota;
	}
	public long getIdEsito() {
		return idEsito;
	}
	public void setIdEsito(long idEsito) {
		this.idEsito = idEsito;
	}
	public EsitoEnum getEsitoEnum() {
		return esitoEnum;
	}
	public void setEsitoEnum(EsitoEnum esitoEnum) {
		this.esitoEnum = esitoEnum;
	}
	public double getQuota() {
		return quota;
	}
	public void setQuota(double quota) {
		this.quota = quota;
	}
	
	

}
