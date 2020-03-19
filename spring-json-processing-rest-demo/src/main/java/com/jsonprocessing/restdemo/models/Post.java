package com.jsonprocessing.restdemo.models;

import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose
    @NonNull
    @Length(min = 3, max = 80)
    private String title;

    @Expose
    @NonNull
    @Length(min = 3, max = 2048)
    private String content;

    @Expose
    @ManyToOne(optional = true)
    private User author;

    @Expose(serialize = false)
    @Transient
    private Long authorId;

    @Expose
    private Date created = new Date();

    @Expose
    private Date modified = new Date();
}
