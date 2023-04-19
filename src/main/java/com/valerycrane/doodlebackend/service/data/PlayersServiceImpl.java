package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.dao.PlayersRepository;
import com.valerycrane.doodlebackend.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayersServiceImpl implements PlayersService {
    private final PlayersRepository playersRepository;

    public PlayersServiceImpl(@Autowired PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    @Override
    public Optional<Player> getPlayer(String accessToken) {
        return  playersRepository.findByAccessToken(accessToken);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playersRepository.findAll();
    }

    @Override
    public void savePlayer(Player player) {
        playersRepository.save(player);
    }
}
