package com.valerycrane.doodlebackend.controller;

import com.valerycrane.doodlebackend.dto.requests.RoomRequest;
import com.valerycrane.doodlebackend.dto.responses.RoomResponse;
import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.service.data.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RoomsController {

    private final RoomsService service;

    public RoomsController(@Autowired RoomsService service) {
        this.service = service;
    }

    @PostMapping("/rooms/{id}/join")
    public RoomResponse joinRoom(
            @PathVariable("id") int roomId,
            @RequestHeader("Authorization") String playerToken
    ) {
        Optional<Room> optionalRoom = service.addPlayerToRoom(playerToken, roomId);
        return optionalRoom.map(RoomResponse::new).orElse(null);
    }

    @PostMapping("/rooms")
    public RoomResponse createRoom(@RequestBody RoomRequest roomRequest) {
        Room createdRoom = service.saveRoom(roomRequest.createRoom());
        return new RoomResponse(createdRoom);
    }
}
