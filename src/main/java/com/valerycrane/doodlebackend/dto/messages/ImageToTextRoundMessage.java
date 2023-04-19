package com.valerycrane.doodlebackend.dto.messages;

import com.valerycrane.doodlebackend.entity.Turn;
import com.valerycrane.doodlebackend.entity.subtypes.RoundType;

public class ImageToTextRoundMessage {

    private final RoundType roundType = RoundType.IMAGE_TO_TEXT;
    private final long duration;
    private final int imageId;

    public ImageToTextRoundMessage(Turn turn) {
        this.duration = turn.getRound().getTimespan();
        this.imageId = Integer.parseInt(turn.getPreviousTurn().getAttachment().payload());
    }
}
