package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.Round;

public interface GameService {
    public void start(Room room);
    public void didFinishRound(Room room, Round round);

}
