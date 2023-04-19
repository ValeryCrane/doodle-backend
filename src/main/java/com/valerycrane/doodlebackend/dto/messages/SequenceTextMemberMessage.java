package com.valerycrane.doodlebackend.dto.messages;

public class SequenceTextMemberMessage extends SequenceMemberMessage {
    private final String text;
    public SequenceTextMemberMessage(int playerId, String playerUsername, String playerAvatar, String text) {
        super(playerId, playerUsername, playerAvatar);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
