package rikkei.academy.md4casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "films")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private Video video;
    @OneToMany
    Set<Category> categories = new HashSet<>();
    @ManyToOne
    private Country country;
    @ManyToOne
    private User user;

}
