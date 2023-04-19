package com.valerycrane.doodlebackend.dto.messages;

import com.valerycrane.doodlebackend.entity.Turn;
import com.valerycrane.doodlebackend.entity.subtypes.RoundType;

public class TextToImageRoundMessage {
    private final RoundType roundType = RoundType.TEXT_TO_IMAGE;
    private final long duration;
    private final String text;

    public TextToImageRoundMessage(Turn turn) {
        this.duration = turn.getRound().getTimespan();
        this.text = turn.getPreviousTurn().getAttachment().payload();
    }
}
