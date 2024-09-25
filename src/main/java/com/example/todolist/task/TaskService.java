package com.example.todolist.task;

import com.example.todolist.exceptions.UserMismatchException;
import com.example.todolist.task.dto.CreateTaskResponse;
import com.example.todolist.task.dto.TaskDto;
import com.example.todolist.task.dto.TaskProjection;
import com.example.todolist.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {


    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
        newTask.setCompleted(task.completed());
        newTask.setPriority(task.priority());
        newTask.setDate_limit(task.date_limit());

        Task savedTask = taskRepository.save(newTask);
        return new CreateTaskResponse(savedTask.getId_task(), savedTask.getTitle(), savedTask.getDescription());
    }

    public Task updateTask(Task task, Long userID) {

        if(!task.getUser().getId_user().equals(userID)) {
            throw new UserMismatchException("User ID does not match.");
        }

        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}
