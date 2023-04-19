package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.entity.Player;

import java.security.Principal;
import java.util.Optional;

public interface SubscribeObserver {
    public void playerDidSubscribe(Optional<Player> player, Principal principal, String topic);
}
