package com.example.todolist.task;

import com.example.todolist.task.dto.CreateTaskResponse;
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

    public List<Task> getAllTasks() {
        Sort sort = Sort.by("priority").descending();
        return taskRepository.findAll(sort);
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
