/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Verwaltet alle Besucher der Bibliothek.
 */
@Service
public class Kundenregister {

	private final BesucherRepository       besucherRepository;
	private final BesucherStatusRepository alleBesucher;


	public Kundenregister(BesucherRepository besucherRepository, BesucherStatusRepository alleBesucher) {
		this.besucherRepository = besucherRepository;
		this.alleBesucher = alleBesucher;
	}

	public boolean addBesucher(Besucher besucher) {
		besucherRepository.save(besucher);
		alleBesucher.save(new BesucherStatus(besucher));
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
		return gibtBuchZurueckIntern(buch, besucher);
	}

	private boolean gibtBuchZurueckIntern(Buch buch, Besucher besucher) {
		if (besucher == null) {
			return false;
		}
		var status = alleBesucher.findByBesucher(besucher);
		boolean back = status.entferneBuchAusAusgelieheneBuecher(buch);
		if (status.getBesucherTyp() != BesucherTyp.Dozent) {
			erhoeheStrafeIntern(besucher.getId(), berechneKosten(buch));
		}
		return back;
	}

	public boolean gibBuchZurueck(Buch buch) {
		var besucher = alleBesucher.findAll()
								   .filter(it -> it.getAusgelieheneBuecher().contains(buch.getId()))
								   .map(BesucherStatus::getBesucher)
								   .toList().getFirst();
		return gibtBuchZurueckIntern(buch, besucher);
	}

	public boolean leiheBuchAus(Buch buch, Besucher besucher) {
		return alleBesucher.findByBesucher(besucher).registriereAusgeliehenesBuch(buch);
	}

	public Double getStrafe(Besucher besucher) {
		try {
			return alleBesucher.findByBesucher(besucher).getStrafe();
		} catch (NullPointerException ne) {
			throw new IllegalArgumentException(ne.getMessage());
		}
	}

	/**
	 * Berechnet die Strafe für ausgeliehene Bücher.
	 *
	 * @param buch Buch, füch das die Strafe berechnet werden soll
	 * @return Strafe, wenn das Buch jetzt zurückgegeben wird.
	 *
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
		alleBesucher.findByBesucher(besucher).bezahlen();
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
		var status = alleBesucher.findByBesucherId(id);
		return status.erhoeheStrafe(kosten);
	}

	public List<UUID> getAusgelieheneBuecher(Besucher besucher) {
		return alleBesucher.findByBesucher(besucher).getAusgelieheneBuecher();
	}


//endregion

}
