package com.bookit.dto.room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequestDTO {
    private String roomName;
    private int capacity;
    private String roomType;
    private String location;
    private boolean hasProjector;
    private boolean hasScreen;
    private boolean hasWhiteboard;
    private boolean isAvailable;
}
