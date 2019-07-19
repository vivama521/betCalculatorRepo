package it.betcalculator.domain;

public enum AggioEnum {

	AGGIO_SCENDE("AGGIO_SCENDE"),
	AGGIO_SALE("AGGIO_SALE"),
	AGGIO_UGUALE("AGGIO_UGUALE");
	
	private String aggio;
	
	private AggioEnum(String aggio) {
		this.aggio = aggio;
	}

	public String getAggio() {
		return aggio;
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
