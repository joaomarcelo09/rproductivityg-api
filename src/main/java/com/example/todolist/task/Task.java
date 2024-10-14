package com.example.todolist.task;


import com.example.todolist.guild.Guild;
import com.example.todolist.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_task;
    private String title;
    private String description;
    private int priority;
    private Boolean completed;
    private String date_limit;
    private String created_at;
    private String time_spent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guild_id")
    private Guild guild;

    public Task(Guild guild,User user, String title, String description, int priority, boolean completed, String date_limit, String created_at, String time_spent) {

        this.guild = guild;
        this.created_at = created_at;
        this.time_spent = time_spent;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.date_limit = date_limit;
        this.user = user;
    }
}
