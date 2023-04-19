package com.valerycrane.doodlebackend.dto.messages;

public class SequenceImageMemberMessage extends SequenceMemberMessage {
    private final int imageId;
    public SequenceImageMemberMessage(int playerId, String playerUsername, String playerAvatar, int imageId) {
        super(playerId, playerUsername, playerAvatar);
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }
}
