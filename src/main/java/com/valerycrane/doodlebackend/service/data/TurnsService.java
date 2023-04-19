package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.Turn;
import org.springframework.stereotype.Service;

public interface TurnsService {
    Turn saveTurn(Turn turn);
}
