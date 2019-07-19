package it.betcalculator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.betcalculator.domain.Match;
import it.betcalculator.service.BetCalculatorService;

@RestController
@RequestMapping(path="/betcalculator") 
public class BetCalculatorController {

	private Logger logger = LoggerFactory.getLogger(BetCalculatorController.class);

	@Autowired
	BetCalculatorService service;

	@GetMapping("/matches")
	public ResponseEntity<List<Match>> matches() {
		List<Match> matches = (List<Match>) service.findAll();
		logger.debug(matches.toString());
		return new ResponseEntity<List<Match>>(matches, HttpStatus.OK);
	}

	@GetMapping("/schedina/{campionato}") 
	public ResponseEntity<List<Match>> schedina(@PathVariable("campionato") String campionato){
		List<Match> schedina = service.probabiliRisultati(campionato);
		return new ResponseEntity<List<Match>>(schedina, HttpStatus.OK);
	}
}


