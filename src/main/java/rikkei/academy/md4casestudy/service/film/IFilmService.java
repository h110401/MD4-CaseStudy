package rikkei.academy.md4casestudy.service.film;

import rikkei.academy.md4casestudy.model.Category;
import rikkei.academy.md4casestudy.model.Film;
import rikkei.academy.md4casestudy.service.IGenericService;

import java.util.Set;

public interface IFilmService extends IGenericService<Film> {
    Boolean existsByName(String name);
//    Boolean existsByCategories( Set<Category> categories);

}
