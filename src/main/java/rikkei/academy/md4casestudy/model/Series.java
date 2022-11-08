package rikkei.academy.md4casestudy.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
