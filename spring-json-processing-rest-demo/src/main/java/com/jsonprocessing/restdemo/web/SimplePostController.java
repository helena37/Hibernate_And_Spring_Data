package com.jsonprocessing.restdemo.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsonprocessing.restdemo.models.Post;
import com.jsonprocessing.restdemo.services.api.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;

@RestController
@RequestMapping("/api/simple")
@Slf4j
public class SimplePostController {
    private final PostService postService;
    private Gson gson;

    @Autowired
    public SimplePostController(PostService postService) {
        this.postService = postService;
    }

    @PostConstruct
    private void init() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPost() {
        return gson.toJson(this.postService.getPosts());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPost(@RequestBody String body) {
        log.info("Body received: {}", body);
        Post post = gson.fromJson(body, Post.class);
        log.info("Post deserialized: {}", post);
        Post created = this.postService.addPost(post);
        URI uri = MvcUriComponentsBuilder
                .fromMethodName(SimplePostController.class, "addPost", post)
                .pathSegment("{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(gson.toJson(this.postService.getPosts()));
    }
}
