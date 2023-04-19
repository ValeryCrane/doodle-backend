package com.valerycrane.doodlebackend.entity;

import com.valerycrane.doodlebackend.entity.subtypes.RoundType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "round", cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    private List<Turn> turns;

    @OneToOne(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "prev_round_id")
    private Round previousRound;

    @OneToOne(mappedBy = "previousRound", cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    private Round nextRound;

    @Column(name = "type")
    private RoundType type;

    @Column(name = "start_unix_date")
    private long startUnixDate;

    @Column(name = "timespan")
    private long timespan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
    }

    public Round getPreviousRound() {
        return previousRound;
    }

    public void setPreviousRound(Round previousRound) {
        this.previousRound = previousRound;
    }

    public Round getNextRound() {
        return nextRound;
    }

    public void setNextRound(Round nextRound) {
        this.nextRound = nextRound;
    }

    public RoundType getType() {
        return type;
    }

    public void setType(RoundType type) {
        this.type = type;
    }

    public long getStartUnixDate() {
        return startUnixDate;
    }

    public void setStartUnixDate(long startUnixDate) {
        this.startUnixDate = startUnixDate;
    }

    public long getTimespan() {
        return timespan;
    }

    public void setTimespan(long timespan) {
        this.timespan = timespan;
    }
}
