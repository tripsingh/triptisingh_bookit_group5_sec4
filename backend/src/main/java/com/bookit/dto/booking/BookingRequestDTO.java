package com.bookit.dto.booking;

import com.bookit.entity.Booking;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
@Getter
@Setter
public class BookingRequestDTO {
        private Long userId;
        private Long roomId;
        private String startTime;
        private String endTime;
        private String purpose;
        private String bookingStatus;
    }


    // Getters and setters
