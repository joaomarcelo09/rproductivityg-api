package com.example.todolist.guild;

import com.example.todolist.exceptions.guild.GuildNotFound;
import com.example.todolist.exceptions.guild.PlayerAlreadyHasGuild;
import com.example.todolist.guild.dto.GuildDto;
import com.example.todolist.guild.dto.GuildResponse;
import com.example.todolist.player.Player;
import com.example.todolist.player.PlayerService;
import org.springframework.stereotype.Service;

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
