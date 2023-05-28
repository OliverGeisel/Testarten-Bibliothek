package de.olivergeisel.teddjbrary.rooms;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoomReposititory extends CrudRepository<Room, UUID> {
}
