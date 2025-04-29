package com.bookit.controller;

import com.bookit.dto.booking.BookingRequestDTO;
import com.bookit.dto.booking.BookingResponseDTO;
import com.bookit.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create a new booking
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO requestDTO) {
        try {
            BookingResponseDTO created = bookingService.createBooking(requestDTO);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            // Return the HTTP status and error message from service
            return new ResponseEntity<>(Map.of("error", ex.getReason()), ex.getStatusCode());
        } catch (Exception ex) {
            // Log unexpected errors
            ex.printStackTrace();
            return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // (Optional) Other endpoints for completeness:

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        List<BookingResponseDTO> list = bookingService.getAllBookingsAsDTO();
        return ResponseEntity.ok(list);
    }

    // Get a booking by ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId) {
        try {
            BookingResponseDTO dto = bookingService.getBookingByIdAsDTO(bookingId);
            return ResponseEntity.ok(dto);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(Map.of("error", ex.getReason()), ex.getStatusCode());
        }
    }

    // Delete a booking
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long bookingId) {
        try {
            bookingService.deleteBooking(bookingId);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(Map.of("error", ex.getReason()), ex.getStatusCode());
        }
    }
}
