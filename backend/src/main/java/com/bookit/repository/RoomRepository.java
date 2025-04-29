package com.bookit.repository;

import com.bookit.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {}