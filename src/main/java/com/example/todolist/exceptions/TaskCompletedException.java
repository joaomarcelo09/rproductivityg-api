package com.example.todolist.exceptions;

public class TaskCompletedException extends RuntimeException {
    public TaskCompletedException() {
        super("Task already completed");
    }
    public TaskCompletedException(String message) {
        super(message);
    }
}
