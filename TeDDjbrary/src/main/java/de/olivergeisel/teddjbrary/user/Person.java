package de.olivergeisel.teddjbrary.user;

/**
 * Personen sind Besucher oder Angestellte einer Bibliothek. Sie haben immer einen Namen, Vornamen und ein Alter.
 * Außerdem können sie ihren vollständigen Namen angeben.
 */
public interface Person {
	// Todo Refactoring hier ist public überflüssig


//region setter/getter
	/**
	 * @return Vorname einer Person.
	 */
	public String getVorname();

	/**
	 * @return Nachname einer Person.
	 */
	public String getNachname();

	/**
	 * @return Vorname plus Nachname.
	 */
	default public String getVollerName() {
		return getVorname() + " " + getNachname();
	}

	/**
	 * @return Alter der Person.
	 */
	public int getAlter();
//endregion

}
