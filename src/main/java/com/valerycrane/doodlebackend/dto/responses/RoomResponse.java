package com.valerycrane.doodlebackend.dto.responses;

import com.valerycrane.doodlebackend.entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoomResponse {
    private int id;
    private String name;
    private int capacity;
    private List<PlayerResponse> players;

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.capacity = room.getCapacity();
        this.players = Optional.ofNullable(room.getPlayers())
                .orElse(Collections.emptyList())
                .stream()
                .map(PlayerResponse::new)
                .toList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public List<PlayerResponse> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerResponse> players) {
        this.players = players;
    }
}
