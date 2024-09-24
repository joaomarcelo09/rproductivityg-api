package com.example.todolist.player;

import com.example.todolist.classes.Classes;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player save(Classes playerClass) {

        Player newPlayer = new Player();
        newPlayer.setPlayerClass(playerClass);
        newPlayer.setLevel(1);
        newPlayer.setIncreaser(64);
        newPlayer.setCurrent_experience(0);
        newPlayer.setExperience_to_up(100);

        this.playerRepository.save(newPlayer);

        return newPlayer;
    }
}
