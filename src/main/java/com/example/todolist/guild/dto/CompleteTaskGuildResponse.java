package com.example.todolist.guild.dto;

import com.example.todolist.player.CompleteTaskGuildPlayerDto;

import java.util.List;

public record CompleteTaskGuildResponse(GuildResponse resGuild, List<CompleteTaskGuildPlayerDto> players) {
}
