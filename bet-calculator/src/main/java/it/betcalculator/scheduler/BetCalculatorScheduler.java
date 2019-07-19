package it.betcalculator.scheduler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import it.betcalculator.domain.Evento;
import it.betcalculator.domain.Match;
import it.betcalculator.repo.MatchRepository;
import it.betcalculator.service.BetCalculatorService;

@Configuration
@EnableScheduling
@Transactional
public class BetCalculatorScheduler {

	private Logger logger = LoggerFactory.getLogger(BetCalculatorScheduler.class);

	@Value("#{${map.championship}}") 
	private Map<String, String> urlCampionati;

	@Autowired
	MatchRepository matchRepository;

	@Autowired
	BetCalculatorService betCalculatorService;

	//@Scheduled(cron = "0 0/5 0 ? * * *")

	// ogni minuto
	//	@Scheduled(cron = "0 * * ? * *")
	public void aggiornaMatch() throws Exception {

		List<Evento> eventiDisponibili = caricaEventiDisponibili();

		logger.debug("Aggiorno dati: " + LocalDateTime.now());
		eventiDisponibili.forEach(evento -> {
			String nazione = evento.getNazione();
			evento.getCampionati().forEach(campionato -> {
				evento.getUrls().forEach(url -> {

					logger.debug("Nazione: " + nazione);
					logger.debug("Campionato: " + campionato);
					logger.debug("Url: " + url);

					Document doc = null;
					try {
						doc = Jsoup.connect("https://www.betexplorer.com" + url).get();
					} catch (IOException e) {
						logger.error("Errore di connessione", e);
					}
					Elements tableMain = getMainTable(doc);
					if(!tableMain.isEmpty()) {
						Element table = getTableEvents(tableMain);
						Elements righe = getRighe(table);
						righe.remove(0);
						righe.forEach(riga-> {
							// LISTA DEI MATCH DISPONIBILI
							Elements squadreElements = riga.getElementsByClass("in-match");

							// QUOTE DEL MATCH DISPONIBILE
							Elements dataOdds = riga.getElementsByAttribute("data-odd");

							if(!dataOdds.isEmpty()) {

								String uno = dataOdds.get(0).attr("data-odd");
								String x = dataOdds.get(1).attr("data-odd");
								String due = dataOdds.get(2).attr("data-odd"); 

								//NOMI SQUADRE PER MATCH
								String[] squadre = squadreElements.get(0).text().split(" - ");

								String casa = squadre[0];
								String ospite = squadre[1];

								// STATO EVENTO
								String statoEvento = riga.getElementsByClass("table-main__eventstage").text();

								// RISULTATO
								String risultato = riga.getElementsByClass("table-main__result").text();

								String initDate = "";
								Elements elementiDate = riga.getElementsByClass("h-text-right");
								if(elementiDate.size() > 1) {
									initDate = elementiDate.get(1).text();
								} else {
									initDate = elementiDate.get(0).text();
								}

								double quota1 = Double.valueOf(uno);
								double quotaX = Double.valueOf(x);
								double quota2 = Double.valueOf(due);

								double oci = 1;

								Match match = new Match(casa, ospite, quota1, quotaX , quota2, risultato, nazione, campionato, oci, initDate);
								match.calcolaAggioIniziale();

								if(!statoEvento.equals("")) {
									match.setStatoEvento(statoEvento);
								}

								try {
									betCalculatorService.save(match);
								} catch (Exception e) {
									logger.error("Db Error: ", e);
								}

							}

						});
					}

				});
			});
		});



	}


	public List<Evento> caricaEventiDisponibili() throws IOException {

		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.betexplorer.com").get();
		} catch (IOException e) {
			logger.error("Errore di connessione", e);
			throw e;
		}

		List<Evento> listaEventi = new ArrayList<Evento>();

		Element tabellaEventiDisponibli = doc.getElementById("upcoming-events-sport-1");
		Elements  tabellaLeft = tabellaEventiDisponibli.getElementsByClass("list-events__item js-country");

		tabellaLeft.forEach(el -> {

			Evento evento = new Evento();

			String nazione = el.getElementsByClass("list-events__item__title list-events__item__title--bold").text();
			evento.setNazione(nazione);

			Element listaEventiPerNazione = el.getElementsByClass("list-events list-events--submenu").get(0);

			Elements competizioni = listaEventiPerNazione.getElementsByTag("li");

			List<String> campionati = new ArrayList<String>();
			List<String> urls = new ArrayList<String>();

			competizioni.forEach(eventoPerNazione -> {
				String url = eventoPerNazione.getElementsByTag("a").attr("href");
				String campionato = eventoPerNazione.getElementsByTag("a").get(0).text();

				campionati.add(campionato);
				urls.add(url);
			});

			evento.setCampionati(campionati);
			evento.setUrls(urls);

			listaEventi.add(evento);
		});

		return listaEventi;

	}

	private Elements getMainTable(Document doc) {
		return doc.getElementsByClass("table-main table-main--leaguefixtures h-mb15");
	}

	private Element getTableEvents(Elements tableMain) {
		return tableMain.get(0);		
	}

	private Elements getRighe(Element table) {
		return table.getElementsByTag("tr");
	}

}
