package com.caue.jdbcjava.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
public class User {
    private Long id;
    private String name;
    private LocalDateTime localDateTime;
}
