package com.jsonprocessing.restdemo.services.api;

import com.jsonprocessing.restdemo.models.Post;

import java.util.Collection;

public interface PostService {
    Collection<Post> getPosts();
    Post addPost(Post post);
    Post updatePost(Post post);
    Post deletePost(Long id);
    long getPostsCount();

}
