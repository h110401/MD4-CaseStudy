package rikkei.academy.md4casestudy.api;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "film")
public class ApiFilm {
    private Long id;
    private String name;

}
