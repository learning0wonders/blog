package com.springboot.blog.service;

import com.springboot.blog.DTO.CommentDTO;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    ModelMapper mapper = new ModelMapper();

    public CommentDTO addCommentToPost(CommentDTO commentDTO, long postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", ""+postId));
        Comment comment = mapToModel(commentDTO);
        comment.setPost(post);
        return mapToDto(commentRepository.save(comment));
    }

    public List<CommentDTO> getAllComments(long postId){
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map((comment)->mapToDto(comment)).collect(Collectors.toList());
    }

    public CommentDTO getCommentByPostId(long postId,long commentId){
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", ""+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", ""+commentId));
        if(comment.getPost().getId() != post.getId())
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        return mapToDto(comment);
    }

    public CommentDTO updateCommentByPostId(long postId,long commentId,CommentDTO commentDTO){
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", ""+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", ""+commentId));
        if(comment.getPost().getId() != post.getId())
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        return mapToDto(commentRepository.save(comment));
    }

    public CommentDTO deleteCommentByPostId(long postId,long commentId){
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", ""+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", ""+commentId));
        if(comment.getPost().getId() != post.getId())
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        commentRepository.deleteById(commentId);
        return mapToDto(comment);
    }

    private CommentDTO mapToDto(Comment comment){
        return mapper.map(comment, CommentDTO.class);
    }
    private Comment mapToModel(CommentDTO commentDTO){
        return mapper.map(commentDTO, Comment.class);
    }
}
