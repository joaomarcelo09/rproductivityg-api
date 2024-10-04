package com.example.todolist.task;

import com.example.todolist.task.dto.TaskProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("FROM Task t WHERE t.user.id_user = :id_user")
    List<TaskProjection> findAllProjectedBy(Long id_user, Sort sort);

    @Query("FROM Task t WHERE t.id_task = :id AND t.user.id_user = :id_user OR t.guild.id_guild = :guild_id")
    Optional<Task> findByIdAndUser_Id(Long id, Long id_user, Long guild_id);
}
