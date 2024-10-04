package com.example.todolist.task;

import com.example.todolist.exceptions.TaskCompletedException;
import com.example.todolist.exceptions.TaskNotFoundException;
import com.example.todolist.guild.Guild;
import com.example.todolist.guild.GuildService;
import com.example.todolist.player.Player;
import com.example.todolist.player.PlayerService;
import com.example.todolist.task.dto.*;
import com.example.todolist.user.User;
import com.example.todolist.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {


    private final TaskRepository taskRepository;
    private final PlayerService playerService;
    private final UserService userService;
    private final GuildService guildService;

    @Autowired
    public TaskService(TaskRepository taskRepository, PlayerService playerService, UserService userService, GuildService guildService) {
        this.taskRepository = taskRepository;
        this.playerService = playerService;
        this.userService = userService;
        this.guildService = guildService;
    }

    public List<TaskProjection> getAllTasks(Long id_user) {
        Sort sort = Sort.by("priority").descending();
        return taskRepository.findAllProjectedBy(id_user, sort);
    }

    public Optional<Task> getTaskById(Long id, Long user_id, Long guild_id) {
        return taskRepository.findByIdAndUser_Id(id, user_id, guild_id);
    }

    public CreateTaskResponse saveTask(TaskDto task, Long user_id) {

        Task newTask = new Task();

        if (task.guild_id() != null) {
            Guild guild = guildService.findById(task.guild_id());
            newTask.setGuild(guild);
        } else {
            User user = userService.findById(user_id);
            newTask.setUser(user);
        }
        newTask.setTitle(task.title());
        newTask.setDescription(task.description());
        newTask.setCreated_at(new Date().toString());
        newTask.setCompleted(false);
        newTask.setPriority(task.priority());
        newTask.setDate_limit(task.date_limit());

        Task savedTask = taskRepository.save(newTask);
        return new CreateTaskResponse(savedTask.getId_task(), savedTask.getTitle(), savedTask.getDescription());
    }

    public UpdateTaskResponse updateTask(UpdateTaskDto task, Long userID) {
        Task upTask = taskRepository.findByIdAndUser_Id(task.id_task(), userID, task.guild_id())
                .orElseThrow(TaskNotFoundException::new);

        TaskMapper.mapUpdateDtoToTask(task, upTask);

        if (upTask.getCompleted()) {
            throw new TaskCompletedException();
        }

        if (task.completed()) {
            Player increasedLevel = playerService.increaseExperience(userID);
            upTask.setTime_spent(new Date().toString());
            UpdateTaskDto resTask = TaskMapper.mapTaskToUpdateDto(upTask);
            taskRepository.save(upTask);
            return new UpdateTaskResponse(
                    resTask,
                    increasedLevel
            );
        } else {
            taskRepository.save(upTask);
        }
        UpdateTaskDto resTask = TaskMapper.mapTaskToUpdateDto(upTask);

        return new UpdateTaskResponse(
                resTask,
                upTask.getUser().getPlayer()
        );
    }

    public void deleteTaskById(Long id, Long userID, Long guild_id) {
        this.taskRepository.findByIdAndUser_Id(id, userID, guild_id)
                .orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
    }
}
