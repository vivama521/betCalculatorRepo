package it.betcalculator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.betcalculator.controller.BetCalculatorController;
import it.betcalculator.domain.Evento;
import it.betcalculator.domain.Match;
import it.betcalculator.repo.MatchRepository;
import it.betcalculator.scheduler.BetCalculatorScheduler;
import it.betcalculator.service.BetCalculatorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BetCalculatorTest {

	private Logger logger = LoggerFactory.getLogger(BetCalculatorTest.class);

	@Autowired
	BetCalculatorScheduler betCalculatorScheduler;

	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	BetCalculatorController controller;
	
	@Autowired
	BetCalculatorService betCalculatorService;

	//@Test
	public void aggiornaMatchConBatch() throws Exception {

		betCalculatorScheduler.aggiornaMatch();
	}
	
	//@Test
	public void caricaCampionati() throws Exception {

		List<Evento> listaEventi = betCalculatorScheduler.caricaEventiDisponibili();
		
		listaEventi.forEach(evento -> {
			logger.debug("Nazione: " + evento.getNazione());
			logger.debug("Campionati: " + evento.getCampionati());
			logger.debug("Urls: " + evento.getUrls());
		});
		
	}
	
	//@Test
	public void probabiliRisultati() {
		List<Match> matches = betCalculatorService.probabiliRisultati("brasilSerieA");
		matches.forEach(match -> {
			logger.debug(match.getCasa() + " - " + match.getOspite());
			logger.debug("Probabile risultato: " + match.getProbabileRisultato());
		});
	}

	//@Test
	public void findByCampionato() {

		String campionato = "liga";

		List<Match> listMatchByCampionato =  matchRepository.findByCampionato(campionato);
		logger.debug("Size list: " + listMatchByCampionato.size());

		logger.debug("Campionato: " + campionato);
	}

	//@Test
	public void findByCasaAndOspite() {
		String casa = "Juventus";
		String ospite = "Torino";

		Optional<Match> optionalMatch = matchRepository.findByCasaAndOspite(casa, ospite);
		if(optionalMatch.isPresent()) {
			logger.debug(optionalMatch.get().toString());
		}

	}
	
	//@Test
	public void findByDataInizioBetween() {
		
		List<Match> listaMatches = matchRepository.findByDataInizioBetweenOrderByDataInizio(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
		loggaListMatch(listaMatches);
		
	}
	
	//@Test
	public void findAllModified() {
		List<Match> listMatch = (List<Match>) matchRepository.findAll();
		List<Match> matchesFiltered = listMatch.stream()
				.filter(match -> match.getCreatedDate().isBefore(match.getModifiedDate()))
				.collect(Collectors.toList());
		
		loggaListMatch(matchesFiltered);
		
	}
	
	//@Test
	public void aggiornaMatchStaticoQuotaAltaCala() {
		Match nuovoMatch = matchRepository.findByCasaAndOspite(	"Kampala City", "Rayon Sport").get();
		nuovoMatch.setUnoFinale(3.25);
		nuovoMatch.setDueFinale(2.55);
		
//		nuovoMatch.setUno(3.95);
//		nuovoMatch.setDue(2.88);
		
		betCalculatorService.save(nuovoMatch);
	}
	
	private void loggaListMatch(List<Match> listaMatches) {
		if(listaMatches.isEmpty()) {
			logger.debug("LISTA MATCH VUOTA!");
		} else {
			listaMatches.forEach(match -> {
				logger.debug(match.getCasa() + " - " + match.getOspite() + " - " + match.getDataInizio());
			});
		}
		
	}
}
