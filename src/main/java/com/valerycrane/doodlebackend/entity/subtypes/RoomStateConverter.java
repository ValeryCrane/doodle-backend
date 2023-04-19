package com.valerycrane.doodlebackend.entity.subtypes;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoomStateConverter implements AttributeConverter<RoomState, String> {
    @Override
    public String convertToDatabaseColumn(RoomState roomState) {
        switch (roomState) {
            case LOBBY -> {
                return "lobby";
            }
            case GAME -> {
                return "game";
            }
            case FINISHED -> {
                return "finished";
            }
        }
        return null;
    }

    @Override
    public RoomState convertToEntityAttribute(String string) {
        switch (string) {
            case "lobby" -> {
                return RoomState.LOBBY;
            }
            case "game" -> {
                return RoomState.GAME;
            }
            case "finished" -> {
                return RoomState.FINISHED;
            }
        }
        return null;
    }
}
