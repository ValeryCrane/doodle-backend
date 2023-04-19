package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayersService {
    List<Player> getAllPlayers();
    Optional<Player> getPlayer(String accessToken);
    void savePlayer(Player player);
}
