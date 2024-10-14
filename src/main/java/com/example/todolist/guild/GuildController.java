package com.example.todolist.guild;

import com.example.todolist.guild.dto.GuildDto;
import com.example.todolist.guild.dto.GuildResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guild")
@RequiredArgsConstructor
public class GuildController {

    private  GuildService guildService;

    @Autowired
    public GuildController(GuildService guildService) {
        this.guildService = guildService;
    }

    @PostMapping
    public ResponseEntity<GuildResponse> createGuild(@RequestBody GuildDto guild) {
        return ResponseEntity.ok(this.guildService.save(guild));
    }
}
