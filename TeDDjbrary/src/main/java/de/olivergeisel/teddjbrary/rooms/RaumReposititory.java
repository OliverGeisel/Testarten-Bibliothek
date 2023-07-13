package de.olivergeisel.teddjbrary.rooms;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RaumReposititory extends CrudRepository<Raum, UUID> {
}
