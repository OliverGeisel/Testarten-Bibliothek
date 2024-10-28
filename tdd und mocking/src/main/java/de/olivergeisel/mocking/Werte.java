package de.olivergeisel.mocking;

public enum Werte {

	SIEBEN,
	ACHT,
	NEUN,
	ZEHN,
	UNTER,
	OBER,
	KOENIG,
	ASS;

	public int getWert(){
		return switch (this){
			case SIEBEN, ACHT, NEUN -> 0;
			case ZEHN -> 10;
			case UNTER -> 2;
			case OBER -> 3;
			case KOENIG -> 4;
			case ASS -> 11;
		};
	}

	public String alsString(){
		return switch (this){
			case SIEBEN -> "7";
			case ACHT -> "8";
			case NEUN -> "9";
			case ZEHN -> "10";
			case UNTER -> "U";
			case OBER -> "O";
			case KOENIG -> "K";
			case ASS -> "A";
		};
	}
}
