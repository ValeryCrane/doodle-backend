package com.valerycrane.doodlebackend.dao;


import com.valerycrane.doodlebackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<Room, Integer> {
}
