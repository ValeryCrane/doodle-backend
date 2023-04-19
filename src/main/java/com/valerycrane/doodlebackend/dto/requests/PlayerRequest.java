package com.valerycrane.doodlebackend.dto.requests;

import com.valerycrane.doodlebackend.entity.Player;

public record PlayerRequest(String username, String avatar) {

    public Player createPlayer() {
        Player player = new Player();
        player.setUsername(username);
        player.setAvatar(avatar);
        return player;
    }
}
