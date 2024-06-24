package com.example.todolist.dto;

public record CreateTaskDto(String title, String description,
                            int priority, Boolean completed,
                            String date_limit) {
}
