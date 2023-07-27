package kz.bitlab.techboot.springfinal.model;


import jakarta.persistence.*;
import lombok.*;
;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_problems")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "tags")
    private String tags;

    @Column(name = "answer")
    private String answer;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @Column(name = "users_solved")
    private int solvedUsersCount = 0;

    @ManyToOne
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> solvedUsers;

    @Column(name = "users_answer")
    private String usersAnswer;

    @Builder.Default
    @Column(name = "status")
    private int status = 0;

    public void addSolverUser(User user){
        solvedUsers.add(user);
    }

    public int solverUsersSize(){
        return solvedUsers.size();
    }
}
