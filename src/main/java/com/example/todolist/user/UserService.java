package com.example.todolist.user;

import com.example.todolist.auth.dto.RegisterRequestDto;
import com.example.todolist.exceptions.UserNotFound;
import com.example.todolist.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFound::new);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(RegisterRequestDto user, Player newPlayer) {

        User newUser = new User();
        newUser.setPlayer(newPlayer);
        newUser.setEmail(user.email());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setName(user.name());

        return userRepository.save(newUser);
    }

    public void update(User user) {
        userRepository.save(user);
    }
}
