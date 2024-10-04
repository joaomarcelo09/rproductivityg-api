package com.example.todolist.exceptions;

public class PlayerNotFound extends RuntimeException {
    public PlayerNotFound() {
        super("Player not found");
    }
}
