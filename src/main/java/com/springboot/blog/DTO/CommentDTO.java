package com.springboot.blog.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;

    @NotEmpty()
    private String name;
    @NotEmpty
    @Email
    private String email;
    @Size(min = 10, message = "Body should be least 10 char long.")
    private String body;
}
