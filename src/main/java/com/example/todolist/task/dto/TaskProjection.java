package com.example.todolist.task.dto;


import org.springframework.beans.factory.annotation.Value;

public interface TaskProjection {
    @Value("#{target.id_task}")
    Long getIdTask();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.priority}")
    String getPriority();

    @Value("#{target.completed}")
    String getCompleted();
}
