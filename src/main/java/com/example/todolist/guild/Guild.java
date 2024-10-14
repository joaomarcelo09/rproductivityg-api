package com.example.todolist.guild;

import com.example.todolist.player.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "guilds")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_guild;

    private String title;
    private Integer level;
    private Integer current_experience;
    private Integer experience_to_up;
    private Integer increaser;

    @OneToMany(mappedBy = "guild")
    private List<Player> players;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_owner")
    private Player owner;

    public Guild(Integer level, Player owner, String title, Integer current_experience, Integer experience_to_up, Integer increaser) {
        this.level = level;
        this.owner = owner;
        this.current_experience = current_experience;
        this.experience_to_up = experience_to_up;
        this.increaser = increaser;
        this.title = title;
    }
}
