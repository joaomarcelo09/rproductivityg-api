package com.example.todolist.guild.dto;

import com.example.todolist.player.Player;

import java.util.List;

public record CompleteTaskGuildDto(GuildResponse resGuild, List<Player> players) {
}
