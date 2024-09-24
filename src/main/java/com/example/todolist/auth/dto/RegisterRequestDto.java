package com.example.todolist.auth.dto;

public record RegisterRequestDto(String name, String email, String password, PlayerRequestDto player ) {
}
