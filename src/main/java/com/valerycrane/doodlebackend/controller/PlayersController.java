package com.valerycrane.doodlebackend.controller;

import com.valerycrane.doodlebackend.dto.requests.PlayerRequest;
import com.valerycrane.doodlebackend.dto.responses.PlayerResponse;
import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.service.data.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Optional;

@RestController
public class PlayersController {

    private final PlayersService service;
    private final TokenController tokenController;

    public PlayersController(@Autowired PlayersService service, @Autowired TokenController tokenController) {
        this.service = service;
        this.tokenController = tokenController;
    }

    @GetMapping("/players")
    public List<PlayerResponse> getAllPlayers() {
        return service.getAllPlayers().stream().map(PlayerResponse::new).toList();
    }

    @GetMapping("/players/self")
    public PlayerResponse getPlayer(@RequestHeader("Authorization") String accessToken, UserPrincipal principal) {
        tokenController.saveAccessTokenForPrincipal(accessToken, principal);
        Optional<Player> player = service.getPlayer(accessToken);
        return player.map(PlayerResponse::new).orElse(null);
    }

    @PostMapping("/players")
    public void savePlayer(@RequestHeader("Authorization") String accessToken, @RequestBody PlayerRequest playerRequest) {
        Optional<Player> optionalPlayer = service.getPlayer(accessToken);
        if (optionalPlayer.isEmpty()) {
            Player player = playerRequest.createPlayer();
            player.setAccessToken(accessToken);
            service.savePlayer(player);
        }
    }

    @PutMapping("/players")
    public void updatePlayer(@RequestHeader("Authorization") String accessToken, @RequestBody PlayerRequest playerRequest) {
        Optional<Player> optionalPlayer = service.getPlayer(accessToken);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setUsername(playerRequest.username());
            player.setAvatar(playerRequest.avatar());
            service.savePlayer(player);
        }
    }
}
