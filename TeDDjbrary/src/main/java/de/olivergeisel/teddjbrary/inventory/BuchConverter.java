package de.olivergeisel.teddjbrary.inventory;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.BuchRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Konvertiert einen Sting in ein Buch. Wird als UUID erwartet und ist die Id des Buches.
 * Durch @Component wird diese automatisch eingebunden und konvertiert die entsprechenden Parameter des Forms.
 *
 * @since 1.1.0
 */
@Component
public class BuchConverter implements Converter<String, Buch> {

	private final BuchRepository buchRepository;

	public BuchConverter(BuchRepository buchRepository) {
		this.buchRepository = buchRepository;
	}

	@Override
	public Buch convert(String id) {
		return buchRepository.findById(UUID.fromString(id)).orElseThrow();
	}
}
