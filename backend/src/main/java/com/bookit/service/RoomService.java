package com.bookit.service;

import com.bookit.entity.Room;
import com.bookit.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get a room by its ID
    public Room getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.orElse(null); // Return null if not found
    }

    // Save a new room
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    // Delete a room by its ID
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    // Additional business logic can be added here, e.g., room availability checks
    public boolean isRoomAvailable(Long roomId) {
        Room room = getRoomById(roomId);
        return room != null && room.isAvailable(); // Assuming isAvailable is a method in Room class
    }
}
