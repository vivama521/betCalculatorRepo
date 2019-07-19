package it.betcalculator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`match`")
public class Match extends AuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_match")
	private Integer idMatch;

	@Column(name = "casa")
	private String casa;

	@Column(name = "ospite")
	private String ospite;

	@Column(name = "uno")
	private Double uno;

	@Column(name = "x")
	private Double x;

	@Column(name = "due")
	private Double due;

	@Column(name = "uno_finale")
	private Double unoFinale;

	@Column(name = "x_finale")
	private Double xFinale;

	@Column(name = "due_finale")
	private Double dueFinale;

	@Column(name = "stato_evento")
	private String statoEvento = "DA GIOCARE";

	@Column(name = "esito_finale")
	private String esitoFinale;

	@Column(name = "oci")
	private Double oci;

	@Column(name = "aggio_iniziale")
	private Double aggioIniziale;

	@Column(name = "aggio_finale")
	private Double aggioFinale;

	@Column(name = "campionato")
	private String campionato;

	@Column(name = "inizio_match")
	private String dataInizio;

	@Column(name = "probabile_risultato")
	private String probabileRisultato;

	@Column(name = "nazione")
	private String nazione;

	public Match() {}

	public Match(String casa, String ospite, Double uno, Double x, Double due, String esitoFinale, String nazione, String campionato, Double oci, String dataInizio) {
		super();
		this.casa = casa;
		this.ospite = ospite;
		this.uno = uno;
		this.x = x;
		this.due = due;
		this.esitoFinale = esitoFinale;
		this.nazione = nazione;
		this.campionato = campionato;
		this.oci = oci;
		this.dataInizio = dataInizio;

	}

	public Integer getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(Integer idMatch) {
		this.idMatch = idMatch;
	}

	public String getCasa() {
		return casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

	public String getOspite() {
		return ospite;
	}

	public void setOspite(String ospite) {
		this.ospite = ospite;
	}

	public Double getUno() {
		return uno;
	}

	public void setUno(Double uno) {
		this.uno = uno;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getDue() {
		return due;
	}

	public void setDue(Double due) {
		this.due = due;
	}

	public String getEsitoFinale() {
		return esitoFinale;
	}

	public void setEsitoFinale(String esitoFinale) {
		this.esitoFinale = esitoFinale;
	}

	public String getStatoEvento() {
		return statoEvento;
	}

	public void setStatoEvento(String statoEvento) {
		this.statoEvento = statoEvento;
	}

	public Double getOci() {
		return oci;
	}

	public void setOci(Double oci) {
		this.oci = oci;
	}

	public String getCampionato() {
		return campionato;
	}

	public void setCampionato(String campionato) {
		this.campionato = campionato;
	}


	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Double getAggioIniziale() {
		return aggioIniziale;
	}

	public void setAggioIniziale(Double aggioIniziale) {
		this.aggioIniziale = aggioIniziale;
	}

	public Double getAggioFinale() {
		return aggioFinale;
	}

	public void setAggioFinale(Double aggioFinale) {
		this.aggioFinale = aggioFinale;
	}

	public Double getUnoFinale() {
		return unoFinale;
	}

	public void setUnoFinale(Double unoFinale) {
		this.unoFinale = unoFinale;
	}

	public Double getxFinale() {
		return xFinale;
	}

	public void setxFinale(Double xFinale) {
		this.xFinale = xFinale;
	}

	public Double getDueFinale() {
		return dueFinale;
	}

	public void setDueFinale(Double dueFinale) {
		this.dueFinale = dueFinale;
	}

	public String getProbabileRisultato() {
		return probabileRisultato;
	}

	public void setProbabileRisultato(String probabileRisultato) {
		this.probabileRisultato = probabileRisultato;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	@Override
	public String toString() {
		return "Match [idMatch=" + idMatch + ", casa=" + casa + ", ospite=" + ospite + ", uno=" + uno + ", x=" + x
				+ ", due=" + due + ", unoFinale=" + unoFinale + ", xFinale=" + xFinale + ", dueFinale=" + dueFinale
				+ ", statoEvento=" + statoEvento + ", esitoFinale=" + esitoFinale + ", oci=" + oci + ", aggioIniziale="
				+ aggioIniziale + ", aggioFinale=" + aggioFinale + ", campionato=" + campionato + ", dataInizio="
				+ dataInizio + ", probabileRisultato=" + probabileRisultato + ", nazione=" + nazione + "]";
	}

	public boolean areQuoteEquals(Match nuovoMatch) {
		boolean uno = false;
		boolean x = false;
		boolean due = false;
		if(getOci() <= 1) {
			uno = getUno().doubleValue() == nuovoMatch.getUno().doubleValue();
			x = getX().doubleValue() == nuovoMatch.getX().doubleValue();
			due = getDue().doubleValue() == nuovoMatch.getDue().doubleValue();
		} else {
			uno = getUnoFinale().doubleValue() == nuovoMatch.getUnoFinale().doubleValue();
			x = getxFinale().doubleValue() == nuovoMatch.getxFinale().doubleValue();
			due = getDueFinale().doubleValue() == nuovoMatch.getDueFinale().doubleValue();
		}
		if(uno && x && due) {
			return true;
		} else {
			return false;
		}
	}		


	public String aggiornaMatch(Match nuovoMatch) {

		String statoAggiornamento = "";

		if(!areQuoteEquals(nuovoMatch) && getOci() <= 1) {
			this.setOci(this.getOci() + 1);
			this.setUnoFinale(nuovoMatch.getUno());
			this.setxFinale(nuovoMatch.getX());
			this.setDueFinale(nuovoMatch.getDue());
			
			statoAggiornamento = "Match aggiornato per la prima volta";
			
		} else if(!areQuoteEquals(nuovoMatch) && getOci() > 1) {
			this.setOci(this.getOci() + 1);
			this.setUnoFinale(nuovoMatch.getUnoFinale());
			this.setxFinale(nuovoMatch.getxFinale());
			this.setDueFinale(nuovoMatch.getDueFinale());
			
			statoAggiornamento = "Match aggiornato";
			
		} else {
			statoAggiornamento = "Nessun cambiamento di quota";
		}
		
		return statoAggiornamento;
	}

	public void calcolaAggioIniziale() {
		this.aggioIniziale = (100/(100/this.uno)+(100/this.x)+(100/this.due));
	}

	public void calcolaAggioFinale() {
		this.aggioFinale = (100/(100/this.unoFinale)+(100/this.xFinale)+(100/this.dueFinale));
	}

	public String vs() {
		return new StringBuilder().append(this.nazione).append("/n").append(this.casa).append(" VS ").append(this.ospite).toString();
	}

}
