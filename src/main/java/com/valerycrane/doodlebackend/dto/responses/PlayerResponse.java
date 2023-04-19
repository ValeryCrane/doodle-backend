package com.valerycrane.doodlebackend.dto.responses;

import com.valerycrane.doodlebackend.entity.Player;

public class PlayerResponse {
    private int id;
    private String username;
    private String avatar;

    public PlayerResponse(Player player) {
        this.id = player.getId();
        this.username = player.getUsername();
        this.avatar = player.getAvatar();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
