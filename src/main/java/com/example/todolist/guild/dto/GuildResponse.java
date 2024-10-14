package com.example.todolist.guild.dto;

public record GuildResponse(
        String title,
        Long playerOwnerId,
        Integer level,
        Integer currentExp,
        Integer expToUp
) {
}


