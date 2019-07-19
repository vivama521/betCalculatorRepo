package it.betcalculator.domain;

public enum EsitoEnum {
	
	UNO ("1", "1X"),
	X ("X", ""),
	DUE ("2", "X2"),
	GOL("gol", "");
	
	
	private String value;
	
	private String doppiaChance;
	
	private EsitoEnum(String value, String doppiaChance) {
		this.value = value;
		this.doppiaChance = doppiaChance;
	}
	
	public String getValue() {
		return this.value;
	}

	public String getDoppiaChance() {
		return doppiaChance;
	}

	
}
