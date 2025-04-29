package com.bookit.service;

import com.bookit.dto.booking.BookingRequestDTO;
import com.bookit.dto.booking.BookingResponseDTO;
import com.bookit.entity.Booking;
import com.bookit.entity.BookingStatus;
import com.bookit.entity.Room;
import com.bookit.entity.User;
import com.bookit.entity.UserType;
import com.bookit.repository.BookingRepository;
import com.bookit.repository.RoomRepository;
import com.bookit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    // 1. Get bookings by user ID
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUser_UserId(userId);
    }

    // 2. Check if the room is available
    public boolean isRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> existingBookings = bookingRepository.findByRoom_RoomId(roomId);
        for (Booking b : existingBookings) {
            if (startTime.isBefore(b.getEndTime()) && endTime.isAfter(b.getStartTime())) {
                return false; // Overlapping booking found
            }
        }
        return true; // No overlaps, room is available
    }

    // 3. Check if the user can book more rooms
    public boolean canUserBookRoom(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getUserType() == UserType.NORMAL) {
            long count = bookingRepository.findByUser_UserId(userId).size();
            return count < 3; // NORMAL users can only have 3 active bookings
        }
        return true; // ADMINS or others have no limit
    }

    // 4. Core Booking logic
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        // Fetch the User and Room properly
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        // Parse times
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(dto.getStartTime(), fmt);
        LocalDateTime endTime = LocalDateTime.parse(dto.getEndTime(), fmt);

        // Business validations
        if (!canUserBookRoom(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User has exceeded booking limit.");
        }
        if (!isRoomAvailable(room.getRoomId(), startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is not available.");
        }

        // Build and save Booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setPurpose(dto.getPurpose());
        booking.setBookingStatus(BookingStatus.valueOf(dto.getBookingStatus()));

        Booking savedBooking = bookingRepository.save(booking);
        return convertToDTO(savedBooking);
    }

    // 5. Convert Entity to DTO
    private BookingResponseDTO convertToDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setRoomId(booking.getRoom().getRoomId());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setPurpose(booking.getPurpose());
        dto.setBookingStatus(booking.getBookingStatus().name());
        dto.setUserName(booking.getUser().getFirstName() + " " + booking.getUser().getLastName());
        dto.setRoomName(booking.getRoom().getRoomName());
        return dto;
    }

    // 6. Get all bookings as DTO
    public List<BookingResponseDTO> getAllBookingsAsDTO() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 7. Get bookings by User ID as DTO
    public List<BookingResponseDTO> getBookingsByUserIdAsDTO(Long userId) {
        return bookingRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 8. Get a booking by its ID
    public BookingResponseDTO getBookingByIdAsDTO(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with id " + bookingId));
        return convertToDTO(booking);
    }

    // 9. Delete a booking
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        bookingRepository.delete(booking);
    }
}
