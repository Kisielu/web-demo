package com.example.webdemo.errorHandling;

public class SDAException extends RuntimeException {

    public SDAException() {
        super();
    }
    public SDAException(String message) {
        super(message);
    }
}
