package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.controller.TokenController;
import com.valerycrane.doodlebackend.dto.messages.TextToImageRoundMessage;
import com.valerycrane.doodlebackend.dto.responses.RoomResponse;
import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.service.data.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoomListService implements SubscribeObserver {
    private final SimpMessagingTemplate messagingTemplate;
    private final RoomsService roomsService;

    public RoomListService(
            @Autowired @Lazy RoomsService roomsService,
            @Autowired SimpMessagingTemplate messagingTemplate,
            @Autowired SubscribeListener subscribeListener
    ) {
        this.roomsService = roomsService;
        this.messagingTemplate = messagingTemplate;
        subscribeListener.addObserver(this);
    }

    public void processRoomListChange(List<Room> rooms) {
        messagingTemplate.convertAndSend(
                "/topic/room",
                Optional.ofNullable(rooms)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(RoomResponse::new)
                        .toList()
        );
    }

    @Override
    public void playerDidSubscribe(Optional<Player> player, Principal principal, String topic) {
        if (Objects.equals(topic, "/user/topic/room")) {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/topic/room",
                    Optional.ofNullable(roomsService.getAllRooms())
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(RoomResponse::new)
                            .toList()
            );
        }
    }
}
