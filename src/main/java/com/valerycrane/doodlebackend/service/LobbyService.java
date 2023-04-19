package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.controller.TokenController;
import com.valerycrane.doodlebackend.dto.responses.RoomResponse;
import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.subtypes.RoomState;
import com.valerycrane.doodlebackend.service.data.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class LobbyService implements SubscribeObserver {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final RoomsService roomsService;
    private final TokenController tokenController;

    public LobbyService(
            @Autowired SimpMessagingTemplate messagingTemplate,
            @Autowired GameService gameService,
            @Autowired @Lazy RoomsService roomsService,
            @Autowired SubscribeListener subscribeListener,
            @Autowired TokenController tokenController
    ) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
        this.roomsService = roomsService;
        this.tokenController = tokenController;
        subscribeListener.addObserver(this);
    }

    public void processRoomChange(Room room) {
        if (room.getState() != RoomState.LOBBY || room.getPlayers() == null) {
            return;
        }

        for (Player player : room.getPlayers()) {
            messagingTemplate.convertAndSendToUser(
                    tokenController.getPrincipalForAccessToken(player.getAccessToken()).getName(),
                    "/topic/room/" + room.getId(),
                    new RoomResponse(room)
            );
        }

        if (room.getPlayers().size() == room.getCapacity()) {
            gameService.start(room);
        }
    }

    @Override
    public void playerDidSubscribe(Optional<Player> player, Principal principal, String topic) {
        Optional<Room> optionalRoom = getRoomFromTopic(topic);
        if (player.isPresent() && optionalRoom.isPresent()) {
            Room room = roomsService.addPlayerToRoom(player.get(), optionalRoom.get());
            processRoomChange(room);
        }
    }

    private Optional<Room> getRoomFromTopic(String topic) {
        if (topic.startsWith("/user/topic/room/")) {
            int roomId = Integer.parseInt(topic.substring("/user/topic/room/".length()));
            return roomsService.getRoomById(roomId);
        } else {
            return Optional.empty();
        }
    }
}
