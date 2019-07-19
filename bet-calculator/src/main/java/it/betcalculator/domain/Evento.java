package it.betcalculator.domain;

import java.util.List;

public class Evento {

	private String nazione;
	private List<String> campionati;
	private List<String> urls;
	
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	public List<String> getCampionati() {
		return campionati;
	}
	public void setCampionati(List<String> campionati) {
		this.campionati = campionati;
	}
	public List<String> getUrls() {
		return urls;
	}
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	
	
	
}