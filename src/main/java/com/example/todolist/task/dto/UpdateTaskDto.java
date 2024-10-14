package com.example.todolist.task.dto;

public record UpdateTaskDto(
        Long id_task,
        String title,
        String description,
        Boolean completed,
        int priority,
        String date_limit,
        Long id_user,
        String created_at,
        String time_spent,
        Long guild_id) {
}
