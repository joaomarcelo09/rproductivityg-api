package com.example.todolist.task;

import com.example.todolist.exceptions.UserMismatchException;
import com.example.todolist.player.Player;
import com.example.todolist.player.PlayerService;
import com.example.todolist.task.dto.*;
import com.example.todolist.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final PlayerService playerService;

    @Autowired
    public TaskService(TaskRepository taskRepository, PlayerService playerService) {
        this.taskRepository = taskRepository;
        this.playerService = playerService;
    }

    public List<TaskProjection> getAllTasks(Long id_user) {
        Sort sort = Sort.by("priority").descending();
        return taskRepository.findAllProjectedBy(id_user, sort);
    }

    public Optional<Task> getTaskById(Long id, Long user_id) {
        return taskRepository.findByIdAndUser_Id(id, user_id);
    }

    public CreateTaskResponse saveTask(TaskDto task, User user) {

        Task newTask = new Task();
        newTask.setUser(user);
        newTask.setTitle(task.title());
        newTask.setDescription(task.description());
        newTask.setCompleted(false);
        newTask.setPriority(task.priority());
        newTask.setDate_limit(task.date_limit());

        Task savedTask = taskRepository.save(newTask);
        return new CreateTaskResponse(savedTask.getId_task(), savedTask.getTitle(), savedTask.getDescription());
    }

    public UpdateTaskResponse updateTask(UpdateTaskDto task, Long userID) {
        Optional<Task> findTask = taskRepository.findById(task.id_task());
        if(findTask.isEmpty()) {
            throw new RuntimeException("Task not found");
        }
        if(!task.id_user().equals(userID)) {
            throw new UserMismatchException("User ID does not match.");
        }

        Task upTask = findTask.get();
        upTask.setId_task(task.id_task());
        upTask.setTitle(task.title());
        upTask.setDescription(task.description());
        upTask.setPriority(task.priority());
        upTask.setDate_limit(task.date_limit());
        upTask.setCompleted(task.completed());
        upTask.setPriority(task.priority());

        UpdateTaskDto resTask = new UpdateTaskDto(
                upTask.getId_task(),
                upTask.getDate_limit(),
                upTask.getTitle(),
                upTask.getCompleted(),
                upTask.getPriority(),
                upTask.getDescription(),
                upTask.getUser().getId_user());

        if(task.completed()){
            Player increasedLevel = playerService.increaseLevel(userID);
            taskRepository.save(upTask);
            return new UpdateTaskResponse(
                    resTask,
                    increasedLevel
            );
        } else {taskRepository.save(upTask);}

        return
                new UpdateTaskResponse(
                        resTask,
                        upTask.getUser().getPlayer()
                        );
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}
