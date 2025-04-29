package com.bookit.dto.booking;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
public class BookingResponseDTO {
    private Long bookingId;
    private Long userId;
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String bookingStatus;
    private String userName;
    private String roomName;


    // Getters and setters
}
