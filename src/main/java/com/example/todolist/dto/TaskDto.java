package com.example.todolist.dto;

//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
public record TaskDto (String title, String description, int priority,
                      Boolean completed, String date_limit, Long user_id ) {}