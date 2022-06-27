package com.springboot.blog.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    private long id;

    @NotEmpty
    @Size(min = 5, message = "Title should be at-least 5 char long.")
    private String title;
    @NotEmpty
    @Size(min = 10, max = 100, message = "Description should be at-least 10 char long.")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
}
