package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.controller.TokenController;
import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.service.data.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscribeListener implements ApplicationListener<SessionSubscribeEvent> {

    private final PlayersService playersService;
    private final TokenController tokenController;
    private final List<SubscribeObserver> observers = new ArrayList<>();

    public SubscribeListener(
            @Autowired PlayersService playersService,
            @Autowired TokenController tokenController
    ) {
        this.playersService = playersService;
        this.tokenController = tokenController;
    }

    public void addObserver(SubscribeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        List<String> headers = headerAccessor.getNativeHeader("Authorization");
        Optional<Player> player = Optional.empty();
        if (headers != null && headers.size() > 0) {
            String playerToken = headers.get(0);
            player = playersService.getPlayer(playerToken);
            tokenController.saveAccessTokenForPrincipal(playerToken, event.getUser());
        }
        for (SubscribeObserver observer : observers) {
            observer.playerDidSubscribe(player, event.getUser(), headerAccessor.getDestination());
        }
    }
}
