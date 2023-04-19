package com.valerycrane.doodlebackend.dto.messages;

import com.valerycrane.doodlebackend.entity.Turn;
import com.valerycrane.doodlebackend.entity.subtypes.RoundType;

public class TextRoundMessage {

    private final RoundType roundType = RoundType.TEXT;
    private final String placeholderPhrase;
    private final long duration;

    public TextRoundMessage(Turn turn) {
        placeholderPhrase = turn.getAttachment().payload();
        duration = turn.getRound().getTimespan();
    }
}
