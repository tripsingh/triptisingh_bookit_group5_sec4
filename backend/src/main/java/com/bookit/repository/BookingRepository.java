package com.bookit.repository;

import com.bookit.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Custom query method to find bookings by user ID
    List<Booking> findByUser_UserId(Long userId);
    List<Booking> findByRoom_RoomId(Long roomId);
// Using 'User_UserId' to match the field in the Booking entity
}
