package com.example.todolist.guild.dto;

public record GuildResponse(
        String title,
        Long ownerName,
        Integer level,
        Integer currentExp,
        Integer expToUp
) {
}


