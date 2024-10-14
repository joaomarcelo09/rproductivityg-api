package com.example.todolist.exceptions.guild;

public class PlayerAlreadyHasGuild extends RuntimeException {
    public PlayerAlreadyHasGuild() {
        super("Player already has a guild");
    }
}
