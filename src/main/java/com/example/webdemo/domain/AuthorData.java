package com.example.webdemo.domain;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthorData {

    private String name;
    private String email;
    private LocalDateTime date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
