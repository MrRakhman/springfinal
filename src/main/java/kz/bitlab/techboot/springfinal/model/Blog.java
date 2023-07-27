package kz.bitlab.techboot.springfinal.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "t_blogs")
@Getter
@Setter
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "post_date")
    private String postDate;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private User author;
}
