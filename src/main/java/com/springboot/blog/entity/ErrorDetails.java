package com.springboot.blog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    private String message;
    private String details;
}
