package kz.bitlab.techboot.springfinal.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_olympiads")
@Getter
@Setter
public class Olympiads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "limitation")
    private String limitation;

//    @Column(name = "author")
//    private String author;

    @ManyToOne(optional = true)
    private User winner;

    @ManyToOne
    private User author;
}
