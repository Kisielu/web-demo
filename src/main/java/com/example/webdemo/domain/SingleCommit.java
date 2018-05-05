package com.example.webdemo.domain;

import org.springframework.stereotype.Component;

@Component
public class SingleCommit {

    private AuthorData author;
    private AuthorData commiter;
    private String message;

    public AuthorData getAuthor() {
        return author;
    }

    public void setAuthor(AuthorData author) {
        this.author = author;
    }

    public AuthorData getCommiter() {
        return commiter;
    }

    public void setCommiter(AuthorData commiter) {
        this.commiter = commiter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
