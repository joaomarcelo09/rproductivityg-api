package com.example.todolist.exceptions.guild;

public class GuildNotFound extends RuntimeException {
    public GuildNotFound() {
        super("Guild not found");
    }
}
