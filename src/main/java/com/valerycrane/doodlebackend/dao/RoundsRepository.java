package com.valerycrane.doodlebackend.dao;


import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundsRepository extends JpaRepository<Round, Integer> {
}
