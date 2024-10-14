package com.example.todolist.guild;

import com.example.todolist.exceptions.guild.GuildNotFound;
import com.example.todolist.exceptions.guild.PlayerAlreadyHasGuild;
import com.example.todolist.guild.dto.CompleteTaskGuildResponse;
import com.example.todolist.guild.dto.GuildDto;
import com.example.todolist.guild.dto.GuildResponse;
import com.example.todolist.player.CompleteTaskGuildPlayerDto;
import com.example.todolist.player.Player;
import com.example.todolist.player.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {
    private final GuildRepository guildRepository;
    private final PlayerService playerService;

    public GuildService(GuildRepository guildRepository, PlayerService playerService) {
        this.guildRepository = guildRepository;
        this.playerService = playerService;
    }

    public Guild findById(long id) {
        return guildRepository.findById(id).orElseThrow(GuildNotFound::new);
    }

    public CompleteTaskGuildResponse increaseExp(Long guildId){

        Guild guild = guildRepository.findById(guildId).orElseThrow(GuildNotFound::new);

        int guildXpIncreased = guild.getCurrent_experience() + 12;
        if(guildXpIncreased >= guild.getExperience_to_up()) {
            guild.setLevel(guild.getLevel() + 1);
            guild.setCurrent_experience(0);
            guild.setExperience_to_up(guild.getExperience_to_up() + guild.getIncreaser());
        } else {
            guild.setCurrent_experience(guildXpIncreased);
        }
        guildRepository.save(guild);

        List<Player> playersToUp = guild.getPlayers();
        List<CompleteTaskGuildPlayerDto> uppedPlayers = new java.util.ArrayList<>(List.of());

        for(Player player : playersToUp) {
            Player upPlayer = playerService.increaseExperience(player);
            uppedPlayers.add(new CompleteTaskGuildPlayerDto(
                    upPlayer.getId_player(),
                    upPlayer.getLevel(),
                    upPlayer.getCurrent_experience(),
                    upPlayer.getExperience_to_up()));
        }

        return new CompleteTaskGuildResponse(new GuildResponse(
                guild.getTitle(),
                guild.getOwner().getId_player(),
                guild.getLevel(),
                guild.getCurrent_experience(),
                guild.getExperience_to_up()
        ), uppedPlayers);
    }

    public GuildResponse save(GuildDto guild) {
        Player player = this.playerService.findById(guild.id_owner());

        if(player.getGuild() != null) {
            throw new PlayerAlreadyHasGuild();
        }

        Guild newGuild = new Guild();
        newGuild.setOwner(player);
        newGuild.setLevel(0);
        newGuild.setCurrent_experience(0);
        newGuild.setExperience_to_up(100);
        newGuild.setTitle(guild.title());
        newGuild.setIncreaser(100);

        player.setGuild(newGuild);

        guildRepository.save(newGuild);
        this.playerService.updatePlayer(player);
        return new GuildResponse(newGuild.getTitle(), player.getId_player(), newGuild.getLevel(),
                newGuild.getCurrent_experience(), newGuild.getExperience_to_up());
    }
}
