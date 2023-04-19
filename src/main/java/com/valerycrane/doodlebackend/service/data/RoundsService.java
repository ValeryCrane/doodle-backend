package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.entity.Round;

import java.util.Optional;

public interface RoundsService {
    Round saveRound(Round round);
    Optional<Round> getRoundById(int id);
}
