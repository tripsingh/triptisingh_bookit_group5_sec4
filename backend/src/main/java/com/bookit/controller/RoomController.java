package com.bookit.controller;

import com.bookit.entity.Room;
import com.bookit.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Get all rooms
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> list = roomService.getAllRooms();
        return ResponseEntity.ok(list);
    }

    // Get a room by ID
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
        Room roomDetails = roomService.getRoomById(roomId);

        if (roomDetails == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = Map.of(
                "roomId", roomDetails.getRoomId(),
                "roomName", roomDetails.getRoomName(),
                "capacity", roomDetails.getCapacity(),
                "roomType", roomDetails.getRoomType(),
                "location", roomDetails.getLocation(),
                "hasProjector", roomDetails.isProjector(),
                "hasScreen", roomDetails.isScreen(),
                "hasWhiteboard", roomDetails.isWhiteboard(),
                "available", roomDetails.isAvailable()
        );

        return ResponseEntity.ok(response);
    }

    // Create a new room
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room createdRoom = roomService.saveRoom(room);
        return ResponseEntity.ok(createdRoom);
    }

    // Delete a room
    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}
