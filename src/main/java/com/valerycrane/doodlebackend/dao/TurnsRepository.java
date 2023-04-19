package com.valerycrane.doodlebackend.dao;

import com.valerycrane.doodlebackend.entity.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnsRepository extends JpaRepository<Turn, Integer> {
}
