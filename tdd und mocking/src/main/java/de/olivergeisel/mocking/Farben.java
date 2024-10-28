package de.olivergeisel.mocking;

public enum Farben {

	SCHELLEN,
	HERZ,
	GRUEN,
	EICHEL;

	public  String getBild(){
		return switch (this){
			case SCHELLEN -> "♦";
			case HERZ -> "♥";
			case GRUEN -> "♣";
			case EICHEL -> "♠";
		};
	}
}
