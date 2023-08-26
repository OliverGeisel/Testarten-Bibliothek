package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

/**
 * Verwaltet alle Besucher der Bibliothek.
 */
@Service
public class Kundenregister {

	private final Map<UUID, BesucherStatus> alleBesucher;

	public Kundenregister() {
		alleBesucher = new HashMap<>();
	}

	public boolean addBesucher(Besucher besucher) {
		var alterEintrag = alleBesucher.put(besucher.getId(), new BesucherStatus(besucher));
		if (alterEintrag != null) {
			alleBesucher.put(besucher.getId(), alterEintrag);
			return false;
		}
		return true;
	}

	/**
	 * Gibt das Buch der Bibliothek zurück.
	 *
	 * @param buch     Buch, das zurückgegeben werden soll.
	 * @param besucher Besucher, der das Buch ausgeliehen hat.
	 * @return true, wenn es keine Probleme gab. Gibt false zurück, wenn Besucher nicht gefunden wird, oder das Buch nicht dem Besucher gehört.
	 */
	public boolean gibBuchZurueck(Buch buch, Besucher besucher) {
		return gibtBuchZurueckIntern(buch, besucher.getId());
	}

	private boolean gibtBuchZurueckIntern(Buch buch, UUID id) {
		var element = alleBesucher.get(id);
		if (element == null) {
			return false;
		}
		boolean back = element.entferneBuchAusAusgelieheneBuecher(buch);
		if (element.getBesucherTyp() != BesucherTyp.Dozent) {
			erhoeheStrafeIntern(id, berechneKosten(buch));
		}
		return back;
	}

	public boolean gibBuchZurueck(Buch buch) {
		UUID besucherID = alleBesucher.entrySet().stream()
									  .filter(it -> it.getValue().getAusgelieheneBuecher().contains(buch))
									  .map(Map.Entry::getKey)
									  .findFirst().orElseThrow();
		return gibtBuchZurueckIntern(buch, besucherID);
	}

	public boolean leiheBuchAus(Buch buch, Besucher besucher) {
		return alleBesucher.get(besucher.getId()).registriereAusgeliehenesBuch(buch);
	}

	public Double getStrafe(Besucher besucher) {
		try {
			return alleBesucher.get(besucher.getId()).getStrafe();
		} catch (NullPointerException ne) {
			throw new IllegalArgumentException(ne.getMessage());
		}
	}

	/**
	 * Berechnet die Strafe für ausgeliehene Bücher.
	 *
	 * @param buch Buch, füch das die Strafe berechnet werden soll
	 * @return Strafe, wenn das Buch jetzt zurückgegeben wird.
	 * @throws IllegalStateException, wenn das Buch nicht ausgeliehen ist.
	 */
	private Double berechneKosten(Buch buch) throws IllegalStateException {
		if (buch.isVerfuegbar()) {
			throw new IllegalStateException("Das Buch ist nicht ausgeliehen");
		}
		LocalDate rueckgabeDatum = buch.getAusleihdatum().plusDays(28);
		if (rueckgabeDatum.isAfter(LocalDate.now())) {
			return 0.0;
		}
		long ueberzogeneTage = Duration.between(rueckgabeDatum.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();

		double kosten = 0.0;
		long ersteTage = Math.min(7, ueberzogeneTage);
		long restTage = ueberzogeneTage - ersteTage;
		// Für die Tage 1-7 wird jeden Tag 1 € berechnet.
		kosten += ersteTage * 1.0;
		long wochen = (restTage) / 7;
		// Ab dem 8. Tag wird jede Woche 5 € verlangt.
		kosten += 5 * Math.min(5, wochen);
		wochen -= Math.min(5, wochen);
		// Ab 43 Tagen wird 2 € die Woche verlangt.
		kosten += wochen * 2;
		// Die Strafe wird nie mehr als 100 € betragen
		return Math.min(100.0, kosten);
	}

	public boolean bezahlen(Besucher besucher) {
		alleBesucher.get(besucher.getId()).bezahlen();
		return true;
	}

	/**
	 * Erhöht die Strafe um den angegebenen Wert.
	 *
	 * @param besucher Kunde, dessen Strafe erhöht wird.
	 * @param betrag   Betrag, um den die Strafe erhöht wird.
	 * @return neuer Betrag der Strafe.
	 */
	Double erhoeheStrafe(Besucher besucher, Double betrag) {
		return erhoeheStrafeIntern(besucher.getId(), betrag);
	}

	private Double erhoeheStrafeIntern(UUID id, Double kosten) {
		var status = alleBesucher.get(id);
		return status.erhoeheStrafe(kosten);
	}

	public List<Buch> getAusgelieheneBuecher(Besucher besucher) {
		return alleBesucher.get(besucher.getId()).getAusgelieheneBuecher();
	}

	/**
	 * Speichert den Staus des Kunden in der Bibliothek.
	 */
	private static final class BesucherStatus {
		private final BesucherTyp besucherTyp;
		private       List<Buch>  ausgelieheneBuecher;
		private       Double      strafe = 0.0;

		private BesucherStatus(Besucher besucher) {
			besucherTyp =
					switch (besucher) {
						case Dozent d -> BesucherTyp.Dozent;
						case Studierender ignored -> BesucherTyp.Studierender;
						default -> BesucherTyp.Normal;
					};
		}

		/**
		 * Bezahlt die Strafe und setzt sie wieder auf 0.
		 */
		void bezahlen() {
			this.strafe = 0.0;
		}

		boolean registriereAusgeliehenesBuch(Buch buch) {
			return ausgelieheneBuecher.add(buch);
		}

		/**
		 * Erhöht die Strafe um den angegebenen Wert.
		 *
		 * @param kosten Betrag, um den die Strafe erhöht wird.
		 * @return neuer Betrag der Strafe.
		 */
		public Double erhoeheStrafe(Double kosten) {
			this.strafe += kosten;
			return strafe;
		}

		public boolean entferneBuchAusAusgelieheneBuecher(Buch buch) {
			return ausgelieheneBuecher.remove(buch);
		}

		//region setter/getter
		public List<Buch> getAusgelieheneBuecher() {
			return Collections.unmodifiableList(ausgelieheneBuecher);
		}

		public BesucherTyp getBesucherTyp() {
			return besucherTyp;
		}

		public Double getStrafe() {
			return strafe;
		}
//endregion
	}


}
