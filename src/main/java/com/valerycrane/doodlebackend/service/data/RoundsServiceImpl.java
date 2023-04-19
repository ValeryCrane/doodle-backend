package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.dao.RoundsRepository;
import com.valerycrane.doodlebackend.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoundsServiceImpl implements RoundsService {
    private final RoundsRepository roundsRepository;

    public RoundsServiceImpl(@Autowired RoundsRepository roundsRepository) {
        this.roundsRepository = roundsRepository;
    }

    @Override
    public Round saveRound(Round round) {
        roundsRepository.save(round);
        return round;
    }

    @Override
    public Optional<Round> getRoundById(int id) {
        return roundsRepository.findById(id);
    }
}
