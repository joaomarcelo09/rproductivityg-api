package com.example.todolist.player;

import com.example.todolist.classes.Classes;

import com.example.todolist.guild.Guild;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_player;

    private Integer level;
    private Integer current_experience;
    private Integer experience_to_up;
    private Integer increaser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_class")
    private Classes playerClass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guild_id")
    private Guild guild;

    public Player(Guild guild,Classes playerClass, Integer level, Integer current_experience, Integer experience_to_up,
            Integer increaser) {
        this.level = level;
        this.current_experience = current_experience;
        this.experience_to_up = experience_to_up;
        this.increaser = increaser;
        this.playerClass = playerClass;
        this.guild = guild;
    }

}
