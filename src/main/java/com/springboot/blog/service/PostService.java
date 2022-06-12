package com.springboot.blog.service;

import com.springboot.blog.DTO.PostDTO;
import com.springboot.blog.DTO.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    private ModelMapper mapper = new ModelMapper();


    public PostDTO createPost(PostDTO post){
        Post postEntity = this.mapper.map(post,Post.class);
        return this.mapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDTO> postDTOS = postList.stream().map((post)->mapper.map(post, PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setLast(posts.isLast());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalItems(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        return postResponse;
    }

    public PostDTO getPostById(long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "Id",  id+""));
        return mapper.map(post, PostDTO.class);
    }

    public PostDTO updatePostById(long id, PostDTO postDTO){
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setId(id);
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        return mapper.map(postRepository.saveAndFlush(post), PostDTO.class);

    }

    public void deletePostById(long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("POST", "Id",  id+""));
        postRepository.delete(post);
    }
}
