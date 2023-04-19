package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.Round;

import java.util.List;
import java.util.Optional;

public interface RoomsService {
    List<Room> getAllRooms();
    Room saveRoom(Room room);
    Room addPlayerToRoom(Player player, Room room);
    Optional<Room> addPlayerToRoom(String playerToken, int roomId);
    Optional<Room> getRoomById(int id);
}
