package com.example.todolist.task.dto;

import com.example.todolist.player.Player;

public record UpdateTaskResponse(UpdateTaskDto task, Player player) {
}
