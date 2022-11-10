package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.md4casestudy.model.Category;
import rikkei.academy.md4casestudy.model.Film;

import java.util.Set;

public interface IFilmRepo extends JpaRepository<Film,Long> {
    Boolean existsByName(String name);

//    Boolean existsByCategories( Set<Category> categories);
}
