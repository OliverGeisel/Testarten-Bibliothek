package de.olivergeisel.teddjbrary.rooms;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false)
	private UUID id;

	private String name;
	private int number;

	public Room() {
	}

	public Room(String name, int number) {
		this.name = name;
		this.number = number;
	}
}
