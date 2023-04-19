package com.valerycrane.doodlebackend.dto.requests;

import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.subtypes.RoomState;

public record RoomRequest(String name, int capacity) {
    public Room createRoom() {
        Room room = new Room();
        room.setName(name);
        room.setCapacity(capacity);
        room.setState(RoomState.LOBBY);
        return room;
    }
}
