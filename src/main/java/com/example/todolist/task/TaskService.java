package com.example.todolist.task;

import com.example.todolist.task.dto.CreateTaskResponse;
import com.example.todolist.task.dto.TaskProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskProjection> getAllTasks(Long id_user) {
        Sort sort = Sort.by("priority").descending();
        return taskRepository.findAllProjectedBy(id_user, sort);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public CreateTaskResponse saveTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return new CreateTaskResponse(savedTask.getId_task(), savedTask.getTitle(), savedTask.getDescription());
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}
