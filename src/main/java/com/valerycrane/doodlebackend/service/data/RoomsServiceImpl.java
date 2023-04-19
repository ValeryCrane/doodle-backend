package com.valerycrane.doodlebackend.service.data;

import com.valerycrane.doodlebackend.dao.PlayersRepository;
import com.valerycrane.doodlebackend.dao.RoomsRepository;
import com.valerycrane.doodlebackend.entity.Player;
import com.valerycrane.doodlebackend.entity.Room;
import com.valerycrane.doodlebackend.service.LobbyService;
import com.valerycrane.doodlebackend.service.RoomListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomsServiceImpl implements RoomsService {

    private final RoomsRepository roomsRepository;
    private final PlayersRepository playersRepository;
    private final RoomListService roomListService;
    private final LobbyService lobbyService;

    public RoomsServiceImpl(
            @Autowired RoomsRepository roomsRepository,
            @Autowired PlayersRepository playersRepository,
            @Autowired RoomListService roomListService,
            @Autowired LobbyService lobbyService
    ) {
        this.roomsRepository = roomsRepository;
        this.playersRepository = playersRepository;
        this.roomListService = roomListService;
        this.lobbyService = lobbyService;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomsRepository.findAll();
    }

    @Override
    public Room saveRoom(Room room) {
        room = roomsRepository.save(room);
        lobbyService.processRoomChange(room);
        roomListService.processRoomListChange(getAllRooms());
        return room;
    }

    @Override
    public Room addPlayerToRoom(Player player, Room room) {
        List<Player> roomPlayers = room.getPlayers() == null ? new ArrayList<>() : new ArrayList<>(room.getPlayers());
        roomPlayers.add(player);
        room.setPlayers(roomPlayers);
        return saveRoom(room);
    }

    @Override
    public Optional<Room> addPlayerToRoom(String playerToken, int roomId) {
        Optional<Room> optionalRoom = roomsRepository.findById(roomId);
        Optional<Player> optionalPlayer = playersRepository.findByAccessToken(playerToken);
        if (optionalRoom.isPresent() && optionalPlayer.isPresent()) {
            Room room = optionalRoom.get();
            Player player = optionalPlayer.get();
            List<Player> roomPlayers = room.getPlayers() == null ? new ArrayList<>() : new ArrayList<>(room.getPlayers());
            roomPlayers.add(player);
            room.setPlayers(roomPlayers);
            return Optional.of(saveRoom(room));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Room> getRoomById(int id) {
        return roomsRepository.findById(id);
    }
}
