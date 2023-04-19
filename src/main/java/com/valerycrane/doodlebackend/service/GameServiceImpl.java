package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.controller.TokenController;
import com.valerycrane.doodlebackend.dto.messages.SequenceMessage;
import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.Round;
import com.valerycrane.doodlebackend.entity.subtypes.RoomState;
import com.valerycrane.doodlebackend.service.data.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private final RoomsService roomService;
    private final RoundService roundService;
    private final SimpMessagingTemplate messagingTemplate;
    private final TokenController tokenController;

    public GameServiceImpl(
            @Autowired @Lazy TokenController tokenController,
            @Autowired @Lazy SimpMessagingTemplate messagingTemplate,
            @Autowired @Lazy RoomsService roomService,
            @Autowired @Lazy RoundService roundService
    ) {
        this.tokenController = tokenController;
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
        this.roundService = roundService;
    }
    @Override
    public void start(Room room) {
        room.setState(RoomState.GAME);
        roomService.saveRoom(room);

        roundService.startTextRound(room);
    }

    @Override
    public void didFinishRound(Room room, Round round) {
        if (room.getRounds().size() == room.getCapacity()) {
            finishGame(room);
        } else {
            switch (round.getType()) {
                case TEXT, IMAGE_TO_TEXT -> roundService.startTextToImageRound(room);
                case TEXT_TO_IMAGE -> roundService.startImageToTextRound(room);
            }
        }
    }

    private void finishGame(Room room) {
        room.setState(RoomState.FINISHED);
        roomService.saveRoom(room);

        Round firstRound = getFirstRound(room);
        List<SequenceMessage> sequences = firstRound.getTurns().stream().map(SequenceMessage::new).toList();

        List<Player> players = room.getPlayers();
        for (Player player : players) {
            messagingTemplate.convertAndSendToUser(
                    tokenController.getPrincipalForAccessToken(player.getAccessToken()).getName(),
                    "/topic/room/" + room.getId(),
                    sequences
            );
        }
    }

    @NonNull
    private Round getFirstRound(Room room) {
        Optional<Round> optionalLastRound = getFirstRoundIfExists(room);
        if (optionalLastRound.isPresent()) {
            return optionalLastRound.get();
        } else {
            // TODO: Выбрасывать нормальное исключение.
            throw new RuntimeException();
        }
    }

    private Optional<Round> getFirstRoundIfExists(Room room) {
        List<Round> rounds = room.getRounds();
        Optional<Round> firstRound = Optional.empty();
        for (Round round : rounds) {
            if (firstRound.isEmpty() || round.getStartUnixDate() < firstRound.get().getStartUnixDate()) {
                firstRound = Optional.of(round);
            }
        }
        return firstRound;
    }
}
