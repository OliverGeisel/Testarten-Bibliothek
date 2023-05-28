package de.olivergeisel.teddjbrary.inventory;

import de.olivergeisel.teddjbrary.core.Buch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface RegalRepository extends CrudRepository<Regal, UUID> {
	@Override
	Streamable<Regal> findAll();

	@NonNull
	Optional<Regal> findFirstByInhalt_Buecher(Buch buch);


}
