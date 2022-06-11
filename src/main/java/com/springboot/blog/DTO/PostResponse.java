package com.springboot.blog.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDTO> content;
    private int pageSize;
    private int pageNo;
    private int totalPages;
    private long totalItems;
    private boolean last;
}
