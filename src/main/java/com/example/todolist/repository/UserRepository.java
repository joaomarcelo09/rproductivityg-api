package com.example.todolist.repository;

import com.example.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE users.id_user = :id", nativeQuery = true)
    User findById(@Param("id") long id);


    Optional<User> findByEmail(String email);

}
