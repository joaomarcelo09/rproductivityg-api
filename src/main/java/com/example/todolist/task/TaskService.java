package com.example.todolist.task;

import com.example.todolist.exceptions.TaskCompletedException;
import com.example.todolist.exceptions.TaskNotFoundException;
import com.example.todolist.player.Player;
import com.example.todolist.player.PlayerService;
import com.example.todolist.task.dto.*;
import com.example.todolist.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        Task upTask = taskRepository.findByIdAndUser_Id(task.id_task(), userID)
                .orElseThrow(TaskNotFoundException::new);

        TaskMapper.mapUpdateDtoToTask(task, upTask);
        UpdateTaskDto resTask = TaskMapper.mapTaskToUpdateDto(upTask);

        if(upTask.getCompleted()) {
            throw new TaskCompletedException();
        }

        if(task.completed()){
            Player increasedLevel = playerService.increaseExperience(userID);
            taskRepository.save(upTask);
            return new UpdateTaskResponse(
                    resTask,
                    increasedLevel
            );
        } else {taskRepository.save(upTask);}

        return new UpdateTaskResponse(
                        resTask,
                        upTask.getUser().getPlayer()
                        );
    }

    public void deleteTaskById(Long id, Long userID) {
        this.taskRepository.findByIdAndUser_Id(id, userID)
                .orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
    }
}
