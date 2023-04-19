package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.entity.Room;

public interface RoundService {
    public void startTextRound(Room room);
    public void startTextToImageRound(Room room);
    public void startImageToTextRound(Room room);
}
