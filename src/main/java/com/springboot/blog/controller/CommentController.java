package com.springboot.blog.controller;


import com.springboot.blog.DTO.CommentDTO;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> addCommentToPost(@RequestBody CommentDTO commentDTO, @PathVariable(name = "postId") long postId){
        return new ResponseEntity(commentService.addCommentToPost(commentDTO,postId), HttpStatus.CREATED);
    }


    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getAllCommentFromPost(@PathVariable(name = "postId") long postId){
        return commentService.getAllComments(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getAllCommentFromPost(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId){
        return ResponseEntity.ok(commentService.getCommentByPostId(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentByPostId(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId, @RequestBody CommentDTO commentDTO ){
        return ResponseEntity.ok(commentService.updateCommentByPostId(postId, commentId, commentDTO));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> deleteCommentByPostId(@PathVariable(name = "postId") long postId, @PathVariable(name = "commentId") long commentId){
        return ResponseEntity.ok(commentService.deleteCommentByPostId(postId, commentId));
    }
}
