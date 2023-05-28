package de.olivergeisel.teddjbrary.user.staff;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AngestellerRepository extends CrudRepository<Angestellter, UUID> {
}
