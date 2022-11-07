package rikkei.academy.md4casestudy.service.category;

import rikkei.academy.md4casestudy.model.Category;
import rikkei.academy.md4casestudy.service.IGenericService;

public interface ICategoryService extends IGenericService<Category> {
    Boolean existsByName(String name);
}
