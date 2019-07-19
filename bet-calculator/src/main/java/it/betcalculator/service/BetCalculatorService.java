package it.betcalculator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.betcalculator.domain.EsitoEnum;
import it.betcalculator.domain.Match;
import it.betcalculator.repo.MatchRepository;

@Service
public class BetCalculatorService {

	private final Logger logger = LoggerFactory.getLogger(BetCalculatorService.class);

	@Autowired
	MatchRepository matchRepository;

	public void save(Match nuovoMatch) {

		logger.debug("New Match: " + nuovoMatch.toString());

		Match match = null;
		String statoMatch = "";

		Optional<Match> optionalMatch = matchRepository.findByCasaAndOspite(nuovoMatch.getCasa(), nuovoMatch.getOspite());
		if(optionalMatch.isPresent()) {
			Match matchEsistente = optionalMatch.get();
			logger.debug("Existing Match: " + matchEsistente.toString());
			logger.debug("Aggiorno match...");
			statoMatch = matchEsistente.aggiornaMatch(nuovoMatch);

			matchRepository.save(matchEsistente);
			match = matchEsistente;
		} else {
			matchRepository.save(nuovoMatch);
			match = nuovoMatch;
		}
		logger.info(statoMatch);
		logger.info(match.vs());

	}

	public List<Match> probabiliRisultati(String campionato) {

		List<Match> matches = matchRepository.findByCampionato(campionato);
		List<Match> probabiliRisultati = new ArrayList<Match>();

		matches.stream().filter(match -> match.getEsitoFinale().equals("") && match.getUno() != match.getDue()).forEach(match -> {
			
			Double oci = match.getOci();

			boolean primoCaso = oci <= 1;
			boolean secondoCaso = (15 < oci && oci < 25);
			boolean terzoCaso = oci > 25;
			
			Map<EsitoEnum, Double> esitiInizialiMap = new HashMap<EsitoEnum, Double>();
			esitiInizialiMap.put(EsitoEnum.UNO, new Double(match.getUno()));
			esitiInizialiMap.put(EsitoEnum.X, new Double(match.getX()));
			esitiInizialiMap.put(EsitoEnum.DUE, new Double(match.getDue()));
			
			Entry<EsitoEnum, Double> entryQuotaPiuBassaIniziale = Collections.min(esitiInizialiMap.entrySet(), (Entry<EsitoEnum, Double> e1, Entry<EsitoEnum, Double> e2) -> e1.getValue()
			        .compareTo(e2.getValue()));
			
			if(primoCaso || secondoCaso || terzoCaso) {
				if(primoCaso) {
					EsitoEnum esitoPrimoCaso = entryQuotaPiuBassaIniziale.getKey();
					match.setProbabileRisultato(esitoPrimoCaso.getValue());
				} 
				if(secondoCaso || terzoCaso) {
					Map<EsitoEnum, Double> esitiFinaliMap = new HashMap<EsitoEnum, Double>();
					esitiFinaliMap.put(EsitoEnum.UNO, new Double(match.getUnoFinale()));
					esitiFinaliMap.put(EsitoEnum.X, new Double(match.getxFinale()));
					esitiFinaliMap.put(EsitoEnum.DUE, new Double(match.getDueFinale()));
					
					Entry<EsitoEnum, Double> entryQuotaPiuAltaIniziale = Collections.max(esitiInizialiMap.entrySet(), (Entry<EsitoEnum, Double> e1, Entry<EsitoEnum, Double> e2) -> e1.getValue()
			        .compareTo(e2.getValue()));
					
					Entry<EsitoEnum, Double> entryQuotaPiuAltaFinale = Collections.max(esitiFinaliMap.entrySet(), (Entry<EsitoEnum, Double> e1, Entry<EsitoEnum, Double> e2) -> e1.getValue()
					        .compareTo(e2.getValue()));
					
					Entry<EsitoEnum, Double> entryQuotaPiuBassaFinale = Collections.min(esitiFinaliMap.entrySet(), (Entry<EsitoEnum, Double> e1, Entry<EsitoEnum, Double> e2) -> e1.getValue()
					        .compareTo(e2.getValue()));
					
					match.calcolaAggioIniziale();
					match.calcolaAggioFinale();
					
					double aggioIniziale = match.getAggioIniziale();
					double aggioFinale = match.getAggioFinale();
					
					//AGGIO SCENDE quota alta cala(0,2 circa)–>> andiamo a puntare sulla quota che inizialmente era più bassa, oppure gol.
					if(aggioIniziale > aggioFinale && entryQuotaPiuAltaIniziale.getValue() > entryQuotaPiuAltaFinale.getValue()) {
						match.setProbabileRisultato(entryQuotaPiuBassaIniziale.getKey().getDoppiaChance() + " oppure GOL");
					}
					
					//AGGIO SCENDE quota bassa cala(0,2 circa)–>> puntiamo sulla quota più bassa.
					if(aggioIniziale > aggioFinale && entryQuotaPiuBassaIniziale.getValue() > entryQuotaPiuBassaFinale.getValue()) {
						match.setProbabileRisultato(entryQuotaPiuBassaFinale.getKey().getDoppiaChance());
					}
					
					//AGGIO SALE quota alta scende (0,4 o di più)—>> un doppia chance sulla quota più alta.
					if(aggioIniziale < aggioFinale && (entryQuotaPiuAltaIniziale.getValue() - entryQuotaPiuAltaFinale.getValue()) > - 0.40 ) {
						match.setProbabileRisultato(entryQuotaPiuAltaFinale.getKey().getDoppiaChance());
					}
					
					//AGGIO SALE quota bassa quota bassa cala—>> quota bassa
					if(aggioIniziale < aggioFinale && entryQuotaPiuBassaIniziale.getValue() > entryQuotaPiuBassaFinale.getValue()) {
						match.setProbabileRisultato(entryQuotaPiuBassaFinale.getKey().getDoppiaChance());
					}
					
				}
				
				// aggiorno il probabile risultato
				matchRepository.save(match);
				probabiliRisultati.add(match);
			}
		});


		return probabiliRisultati;
	}

	public List<Match> findAll() {
		return (List<Match>) matchRepository.findAll();
	}

}
