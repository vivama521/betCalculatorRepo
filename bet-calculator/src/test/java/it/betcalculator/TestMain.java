package it.betcalculator;

import it.betcalculator.domain.AggioEnum;
import it.betcalculator.domain.Match;

public class TestMain {

	public static void main(String[] args) {

		double aggioIniziale = 50.58;
		double aggioFinale = 53.52;
		
		Match match = new Match();
		
	}

	public void probabileRisultato(Double aggioIniziale, Double aggioFinale) {

		AggioEnum aggioEnum = aggio(aggioIniziale, aggioFinale);
		
		
		switch (aggioEnum) {
		case AGGIO_SALE:
			
			break;
		case AGGIO_SCENDE:

			break;

		case AGGIO_UGUALE:
			System.out.println("Nessun variamento di quota");
			break;

		}
	}

	public AggioEnum aggio(Double aggioIniziale, Double aggioFinale) {
		int result = aggioIniziale.compareTo(aggioFinale);
		if(result > 0) {
			return AggioEnum.AGGIO_SCENDE;
		} else if(result < 0) {
			return AggioEnum.AGGIO_SALE;
		} else {
			return AggioEnum.AGGIO_UGUALE;
		}

	}
}