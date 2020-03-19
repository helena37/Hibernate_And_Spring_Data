package com.jsonprocessing.restdemo.models;

import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose
    @NonNull
    @Length(min = 2, max = 15)
    @Column(name = "first_name")
    private String firstName;

    @Expose
    @NonNull
    @Length(min = 2, max = 30)
    @Column(name = "last_name")
    private String lastName;

    @Expose
    @NonNull
    @Length(min = 5, max = 15)
    @Column(unique = true, nullable = false)
    @NotNull
    private String username;

    @Expose(serialize = false)
    @NonNull
    @Length(min = 5, max = 30)
    @Column(unique = true, nullable = false)
    @NotNull
    private String password;

    @Expose
    @NotNull
    @NonNull
    private String role = "ROLE_USER";

    private boolean isActive = true;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private Set<Post> postSet = new HashSet<>();

    @Expose
    private Date created = new Date();

    @Expose
    private Date modified = new Date();

}
