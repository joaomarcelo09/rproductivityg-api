package com.example.todolist.player;

import com.example.todolist.classes.Classes;
import com.example.todolist.exceptions.PlayerNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
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

    public Player increaseExperience(Player player) {

        int playerXpIncreased = player.getCurrent_experience() + 12;

        if (playerXpIncreased >= player.getExperience_to_up()) {
            player.setLevel(player.getLevel() + 1);
            player.setCurrent_experience(0);
            player.setExperience_to_up(player.getExperience_to_up() + player.getIncreaser());
        } else {
            player.setCurrent_experience(playerXpIncreased);
        }

        playerRepository.save(player);

        return player;
    }

    public void updatePlayer(Player player) {
        this.playerRepository.save(player);
    }

    public Player findById(Long playerID) {
        return this.playerRepository.findById(playerID).orElseThrow(PlayerNotFound::new);
    }

}
