package com.jsonprocessing.restdemo.init;

import com.jsonprocessing.restdemo.models.Post;
import com.jsonprocessing.restdemo.models.User;
import com.jsonprocessing.restdemo.services.api.PostService;
import com.jsonprocessing.restdemo.services.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final PostService postService;
    private final UserService userService;

    private static final List<Post> SAMPLE_POST = List.of(
            new Post("Welcome to Spring Data", "Developing data access object with Spring Data is easy."),
            new Post("Reactive Spring Data", "Check R2DBC for reactive JDBC API..."),
            new Post("Now in Spring 5", "Webflux provides reactive and non-blocking web service implementation.")
    );

    private static final List<User> SAMPLE_USERS = List.of(
            new User("Elena", "Hristoskova", "admin", "admin"),
            new User("Verena", "Dietl", "verena80", "Verena80"),
            new User("Borislav", "Hristoskov", "borislav79", "Borislav79")
    );

    @Autowired
    public DataInitializer(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        SAMPLE_USERS.forEach(this.userService::addUser);
        SAMPLE_POST.forEach(post -> {
            post.setAuthor(this.userService.getUserById(1L));
            this.postService.addPost(post);
        });
    }
}
