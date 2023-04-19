package com.valerycrane.doodlebackend.dto.messages;

import com.valerycrane.doodlebackend.entity.Turn;

import java.util.ArrayList;
import java.util.List;

public class SequenceMessage {
    private final int sequenceId;
    private final List<SequenceMemberMessage> sequenceMembers;

    public SequenceMessage(Turn turn) {
        this.sequenceId = turn.getId();
        this.sequenceMembers = new ArrayList<>();
        Turn currentTurn = turn;
        while (currentTurn != null) {
            switch (currentTurn.getAttachment().type()) {
                case TEXT -> sequenceMembers.add(new SequenceTextMemberMessage(
                        currentTurn.getPlayer().getId(),
                        currentTurn.getPlayer().getUsername(),
                        currentTurn.getPlayer().getAvatar(),
                        currentTurn.getAttachment().payload()
                ));
                case IMAGE -> sequenceMembers.add(new SequenceImageMemberMessage(
                        currentTurn.getPlayer().getId(),
                        currentTurn.getPlayer().getUsername(),
                        currentTurn.getPlayer().getAvatar(),
                        Integer.parseInt(currentTurn.getAttachment().payload())
                ));
            }

            currentTurn = currentTurn.getNextTurn();
        }
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public List<SequenceMemberMessage> getSequenceMembers() {
        return sequenceMembers;
    }
}
