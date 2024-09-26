package com.example.todolist.task;

import com.example.todolist.task.dto.*;
import com.example.todolist.user.User;
import com.example.todolist.user.UserService;
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

    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@RequestBody TaskDto task, @RequestAttribute("userId") Long userId) {
        Optional<User> user = this.userService.findById(userId);
        return user.map(value -> ResponseEntity.ok(this.taskService.saveTask(task, value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping
    public ResponseEntity<UpdateTaskResponse> updateTask(@RequestBody UpdateTaskDto task, @RequestAttribute("userId") Long userId) {
        UpdateTaskResponse upTask = this.taskService.updateTask(task, userId);
        return ResponseEntity.ok(upTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskProjection>> getTasks(@RequestAttribute("userId") Long userId) {

        List<TaskProjection> tasks = this.taskService.getAllTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, @RequestAttribute("userId") Long userId) {

        Optional<Task> tasks = this.taskService.getTaskById(id, userId);

        if(tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        this.taskService.deleteTaskById(id);

        return ResponseEntity.ok("Deletado com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable("id") Long id, @RequestAttribute("userId") Long userId) {

        Optional<Task> tasks = this.taskService.getTaskById(id, userId);

        if(tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tasks);
    }
}
