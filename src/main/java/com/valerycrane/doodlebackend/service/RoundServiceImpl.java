package com.valerycrane.doodlebackend.service;

import com.valerycrane.doodlebackend.controller.TokenController;
import com.valerycrane.doodlebackend.dto.messages.ImageToTextRoundMessage;
import com.valerycrane.doodlebackend.dto.messages.TextRoundMessage;
import com.valerycrane.doodlebackend.dto.messages.TextToImageRoundMessage;
import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.entity.Round;
import com.valerycrane.doodlebackend.entity.Turn;
import com.valerycrane.doodlebackend.entity.subtypes.Attachment;
import com.valerycrane.doodlebackend.entity.subtypes.AttachmentType;
import com.valerycrane.doodlebackend.entity.subtypes.RoundType;
import com.valerycrane.doodlebackend.service.data.RoomsService;
import com.valerycrane.doodlebackend.service.data.RoundsService;
import com.valerycrane.doodlebackend.service.data.TurnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoundServiceImpl implements RoundService {

    private final long kTextRoundTime;
    private final long kTextToImageRoundTime;
    private final long kImageToTextRoundTime;

    private final TokenController tokenController;
    private final TurnsService turnsService;
    private final RoundsService roundsService;
    private final RoomsService roomsService;
    private final SimpMessagingTemplate messagingTemplate;
    private final RandomPhrasesService randomPhrasesService;
    private final GameService gameService;

    public RoundServiceImpl(
            @Autowired TokenController tokenController,
            @Autowired TurnsService turnsService,
            @Autowired RoundsService roundsService,
            @Autowired RoomsService roomsService,
            @Autowired SimpMessagingTemplate messagingTemplate,
            @Autowired RandomPhrasesService randomPhrasesService,
            @Autowired GameService gameService
    ) {
        this.tokenController = tokenController;
        this.turnsService = turnsService;
        this.roundsService = roundsService;
        this.roomsService = roomsService;
        this.messagingTemplate = messagingTemplate;
        this.randomPhrasesService = randomPhrasesService;
        this.gameService = gameService;
        kTextRoundTime = 10;
        kTextToImageRoundTime = 30;
        kImageToTextRoundTime = 10;
    }

    @Override
    public void startTextRound(Room room) {
        Round round = new Round();
        round.setRoom(room);
        round.setStartUnixDate(System.currentTimeMillis() / 1000L);
        round.setTimespan(kTextRoundTime);
        round.setType(RoundType.TEXT);
        round = roundsService.saveRound(round);

        List<Player> players = room.getPlayers();
        List<String> phrases = randomPhrasesService.generateRandomPhrases(players.size());

        for (int i = 0; i < players.size(); ++i) {
            Turn turn = new Turn();
            turn.setAttachment(new Attachment(AttachmentType.TEXT, phrases.get(i)));
            turn.setPlayer(players.get(i));
            turn.setRound(round);
            turn = turnsService.saveTurn(turn);

            messagingTemplate.convertAndSendToUser(
                    tokenController.getPrincipalForAccessToken(players.get(i).getAccessToken()).getName(),
                    "/topic/room/" + room.getId(),
                    new TextRoundMessage(turn)
            );
        }

        scheduleRoundFinish(round);
    }

    @Override
    public void startTextToImageRound(Room room) {
        Round lastRound = getLastRound(room);
        List<Turn> turns = lastRound.getTurns();
        Collections.shuffle(turns);

        Round round = new Round();
        round.setRoom(room);
        round.setStartUnixDate(System.currentTimeMillis() / 1000L);
        round.setTimespan(kTextToImageRoundTime);
        round.setType(RoundType.TEXT_TO_IMAGE);
        round.setPreviousRound(lastRound);
        round = roundsService.saveRound(round);

        List<Player> players = room.getPlayers();
        for (int i = 0; i < players.size(); ++i) {
            Turn turn = new Turn();
            turn.setAttachment(new Attachment(AttachmentType.IMAGE, ""));
            turn.setPlayer(players.get(i));
            turn.setRound(round);
            turn.setPreviousTurn(turns.get(i));
            turn = turnsService.saveTurn(turn);

            messagingTemplate.convertAndSendToUser(
                    tokenController.getPrincipalForAccessToken(players.get(i).getAccessToken()).getName(),
                    "/topic/room/" + room.getId(),
                    new TextToImageRoundMessage(turn)
            );
        }

        scheduleRoundFinish(round);
    }

    @Override
    public void startImageToTextRound(Room room) {
        Round lastRound = getLastRound(room);
        List<Turn> turns = lastRound.getTurns();
        Collections.shuffle(turns);

        Round round = new Round();
        round.setRoom(room);
        round.setStartUnixDate(System.currentTimeMillis() / 1000L);
        round.setTimespan(kImageToTextRoundTime);
        round.setType(RoundType.IMAGE_TO_TEXT);
        round.setPreviousRound(lastRound);
        round = roundsService.saveRound(round);

        List<Player> players = room.getPlayers();
        for (int i = 0; i < players.size(); ++i) {
            Turn turn = new Turn();
            turn.setAttachment(new Attachment(AttachmentType.TEXT, ""));
            turn.setPlayer(players.get(i));
            turn.setRound(round);
            turn.setPreviousTurn(turns.get(i));
            turn = turnsService.saveTurn(turn);

            messagingTemplate.convertAndSendToUser(
                    tokenController.getPrincipalForAccessToken(players.get(i).getAccessToken()).getName(),
                    "/topic/room/" + room.getId(),
                    new ImageToTextRoundMessage(turn)
            );
        }

        scheduleRoundFinish(round);
    }

    private void scheduleRoundFinish(Round round) {
        int roomId = round.getRoom().getId();
        int roundId = round.getId();
        long delay = round.getTimespan();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finishRound(roomId, roundId);
            }
        }, delay * 1000);
    }

    private void finishRound(int roomId, int roundId) {
        Optional<Room> optionalRoom = roomsService.getRoomById(roomId);
        Optional<Round> optionalRound = roundsService.getRoundById(roundId);
        if (optionalRoom.isPresent() && optionalRound.isPresent()) {
            gameService.didFinishRound(optionalRoom.get(), optionalRound.get());
        }
    }

    @NonNull
    private Round getLastRound(Room room) {
        Optional<Round> optionalLastRound = getLastRoundIfExists(room);
        if (optionalLastRound.isPresent()) {
            return optionalLastRound.get();
        } else {
            // TODO: Выбрасывать нормальное исключение.
            throw new RuntimeException();
        }
    }

    private Optional<Round> getLastRoundIfExists(Room room) {
        List<Round> rounds = room.getRounds();
        Optional<Round> lastRound = Optional.empty();
        for (Round round : rounds) {
            if (lastRound.isEmpty() || round.getStartUnixDate() > lastRound.get().getStartUnixDate()) {
                lastRound = Optional.of(round);
            }
        }
        return lastRound;
    }
}
