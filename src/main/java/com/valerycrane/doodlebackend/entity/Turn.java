package com.valerycrane.doodlebackend.entity;

import com.valerycrane.doodlebackend.entity.subtypes.Attachment;
import com.valerycrane.doodlebackend.entity.subtypes.AttachmentConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "turns")
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "prev_turn_id")
    private Turn previousTurn;

    @OneToOne(mappedBy = "previousTurn", cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    private Turn nextTurn;

    @ManyToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "round_id")
    private Round round;

    @ManyToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "attachment")
    @Convert(converter = AttachmentConverter.class)
    private Attachment attachment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Turn getPreviousTurn() {
        return previousTurn;
    }

    public void setPreviousTurn(Turn previousTurn) {
        this.previousTurn = previousTurn;
    }

    public Turn getNextTurn() {
        return nextTurn;
    }

    public void setNextTurn(Turn nextTurn) {
        this.nextTurn = nextTurn;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
