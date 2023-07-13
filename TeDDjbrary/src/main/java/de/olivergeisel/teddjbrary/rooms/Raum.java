package de.olivergeisel.teddjbrary.rooms;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Raum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;
    private int number;

    public Raum() {
    }

    public Raum(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
