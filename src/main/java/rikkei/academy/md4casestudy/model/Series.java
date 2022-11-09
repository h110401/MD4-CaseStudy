package rikkei.academy.md4casestudy.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "series")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String poster;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    private Set<Category> category;
    @ManyToOne
    @NotNull
    private Country country;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "series_videos",
            joinColumns = {@JoinColumn(name = "series_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "videos_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "videos_key")
    private Map<Integer, Video> videos;

    @ManyToOne
    @NotNull
    private User user;
}
