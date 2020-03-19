package com.jsonprocessing.restdemo.services.impl;

import com.jsonprocessing.restdemo.dao.PostRepository;
import com.jsonprocessing.restdemo.dao.UserRepository;
import com.jsonprocessing.restdemo.exceptions.InvalidEntityException;
import com.jsonprocessing.restdemo.models.Post;
import com.jsonprocessing.restdemo.models.User;
import com.jsonprocessing.restdemo.services.api.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<Post> getPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        Long authorId;
        if (post.getAuthor() != null && post.getAuthor().getId() != null) {
            authorId = post.getAuthor().getId();
        } else {
            authorId = post.getAuthorId();
        }

        if (authorId != null) {
            User author = this.userRepository.findById(authorId)
                    .orElseThrow(() -> new InvalidEntityException("Author with ID=" + authorId + " does not exist"));
        post.getAuthor();
        }

        if(post.getCreated() == null) {
            post.setCreated(new Date());
        }

        post.setModified(post.getCreated());
        return this.postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public Post deletePost(Long id) {
        return null;
    }

    @Override
    public long getPostsCount() {
        return 0;
    }
}
