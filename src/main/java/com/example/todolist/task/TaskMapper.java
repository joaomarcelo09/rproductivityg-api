package com.example.todolist.task;

import com.example.todolist.task.dto.UpdateTaskDto;

public class TaskMapper {

    public static void mapUpdateDtoToTask(UpdateTaskDto updateTaskDto, Task task) {
        task.setId_task(updateTaskDto.id_task());
        task.setTitle(updateTaskDto.title());
        task.setDescription(updateTaskDto.description());
        task.setPriority(updateTaskDto.priority());
        task.setDate_limit(updateTaskDto.date_limit());
        task.setCompleted(updateTaskDto.completed());
        task.setPriority(updateTaskDto.priority());
    }

    public static UpdateTaskDto mapTaskToUpdateDto(Task task) {
        return new UpdateTaskDto(
                task.getId_task(),
                task.getDate_limit(),
                task.getTitle(),
                task.getCompleted(),
                task.getPriority(),
                task.getDescription(),
                task.getUser().getId_user(),
                task.getCreated_at(),
                task.getTime_spent(),
                task.getGuild().getId_guild()
        );
    }
}
