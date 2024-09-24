package com.example.todolist.auth;

import com.example.todolist.auth.dto.LoginRequestDto;
import com.example.todolist.auth.dto.RegisterRequestDto;
import com.example.todolist.auth.dto.ResponseDto;
import com.example.todolist.classes.Classes;
import com.example.todolist.classes.ClassesService;
import com.example.todolist.player.Player;
import com.example.todolist.player.PlayerService;
import com.example.todolist.user.User;
import com.example.todolist.infra.security.TokenService;
import com.example.todolist.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ClassesService classesService;
    private final PlayerService playerService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto body) {
        User user = this.userService.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterRequestDto body) {
        Optional<User> user = this.userService.findByEmail(body.email());
        Optional<Classes> playerClass = this.classesService.findById(body.player().id_class());


        if (user.isEmpty()) {
            if(playerClass.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Player newPlayer = new Player();
            newPlayer.setPlayerClass(playerClass.get());
            newPlayer.setLevel(1);
            newPlayer.setIncreaser(64);
            newPlayer.setCurrent_experience(0);
            newPlayer.setExperience_to_up(100);

            User newUser = new User();
            newUser.setPlayer(newPlayer);
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setName(body.name());

            this.playerService.save(newPlayer);
            this.userService.save(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
