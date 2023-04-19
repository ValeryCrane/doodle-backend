package com.valerycrane.doodlebackend.dto.messages;

import java.util.Objects;

public class SequenceMemberMessage {
    private final int playerId;
    private final String playerUsername;
    private final String playerAvatar;

    public SequenceMemberMessage(int playerId, String playerUsername, String playerAvatar) {
        this.playerId = playerId;
        this.playerUsername = playerUsername;
        this.playerAvatar = playerAvatar;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public String getPlayerAvatar() {
        return playerAvatar;
    }
}
