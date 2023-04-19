package com.valerycrane.doodlebackend.dao;

import com.valerycrane.doodlebackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayersRepository extends JpaRepository<Player, Integer> {
    Optional<Player> findByAccessToken(String accessToken);
}
