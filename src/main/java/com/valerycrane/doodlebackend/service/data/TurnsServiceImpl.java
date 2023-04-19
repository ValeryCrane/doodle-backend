package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.dao.TurnsRepository;
import com.valerycrane.doodlebackend.entity.Turn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnsServiceImpl implements TurnsService{

    private final TurnsRepository turnsRepository;

    public TurnsServiceImpl(@Autowired TurnsRepository turnsRepository) {
        this.turnsRepository = turnsRepository;
    }

    @Override
    public Turn saveTurn(Turn turn) {
        turnsRepository.save(turn);
        return turn;
    }
}
