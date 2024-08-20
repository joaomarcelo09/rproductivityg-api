package com.example.todolist.task;

import com.example.todolist.task.dto.CreateTaskResponse;
import com.example.todolist.task.dto.TaskDto;
import com.example.todolist.task.dto.TaskProjection;
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

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@RequestBody TaskDto task) {
        Optional<User> user = this.userService.findById(task.user_id());
        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User userExists = user.get();

        Task newTask = new Task();
        newTask.setUser(userExists);
        newTask.setTitle(task.title());
        newTask.setDescription(task.description());
        newTask.setCompleted(task.completed());
        newTask.setPriority(task.priority());
        newTask.setDate_limit(task.date_limit());

        return ResponseEntity.ok(this.taskService.saveTask(newTask));
    }

    @PatchMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        Task upTask = this.taskService.updateTask(task);
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
