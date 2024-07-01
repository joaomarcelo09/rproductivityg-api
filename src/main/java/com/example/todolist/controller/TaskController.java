package com.example.todolist.controller;

import com.example.todolist.dto.TaskDto;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private TaskService taskService;
    private UserRepository userService;

    public TaskController(TaskService taskService, UserRepository userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity createTask(@RequestBody TaskDto task) {
        Optional<User> user = userService.findById(task.user_id());
        Task newTask = new Task();
        newTask.setUser(user);
        newTask.setTitle(task.title());
        newTask.setDescription(task.description());
        newTask.setCompleted(task.completed());
        newTask.setPriority(task.priority());
        newTask.setDate_limit(task.date_limit());

        return ResponseEntity.ok(this.taskService.saveTask(newTask));
    };

    @PatchMapping
    public ResponseEntity updateTask(@RequestBody Task task) {
        Task upTask = this.taskService.updateTask(task);
        return ResponseEntity.ok(upTask);
    };

    @GetMapping
    public ResponseEntity getTasks() {
        var task = this.taskService.getAllTasks();
        return ResponseEntity.ok(task);
    }
}
