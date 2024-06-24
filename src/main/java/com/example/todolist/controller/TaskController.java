package com.example.todolist.controller;

import com.example.todolist.dto.CreateTaskDto;
import com.example.todolist.entity.Task;
import com.example.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping("/")
    public ResponseEntity createTask(@RequestBody Task task) {
        this.taskService.saveTask(task);
        return ResponseEntity.ok("Criado");
    };

}
