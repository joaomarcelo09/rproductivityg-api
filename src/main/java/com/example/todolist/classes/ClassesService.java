package com.example.todolist.classes;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassesService {

    private final ClassesRepository classesRepository;
    public ClassesService(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    public Optional<Classes> findById(Long id) {
        return classesRepository.findById(id);
    }
}
