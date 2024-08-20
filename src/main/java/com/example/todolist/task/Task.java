package com.example.todolist.task;


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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Task(User user, String title, String description, int priority, boolean completed, String date_limit) {

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.date_limit = date_limit;
        this.user = user;
    }
}
