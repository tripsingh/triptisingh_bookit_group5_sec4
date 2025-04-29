package com.bookit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_name", nullable = false, length = 100)
    private String roomName;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "room_type", length = 50)
    private String roomType;

    @Column(length = 100)
    private String location;

    // ✅ Renamed to match JavaBean standards for boolean fields
    @Column(name = "has_projector")
    private boolean projector;

    @Column(name = "has_screen")
    private boolean screen;

    @Column(name = "has_whiteboard")
    private boolean whiteboard;

    @Column(name = "is_available")
    private boolean available;
}
