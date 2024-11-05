package de.olivergeisel.teddjbrary.user.visitor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.UUID;

public interface BesucherStatusRepository extends CrudRepository<BesucherStatus, UUID> {

	@Override
	Streamable<BesucherStatus> findAll();

	BesucherStatus findByBesucher(Besucher besucher);

	BesucherStatus findByBesucherId(UUID besucherId);

}
