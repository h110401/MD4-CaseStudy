package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.md4casestudy.model.Category;

public interface ICateRepo extends JpaRepository<Category,Long> {
    Boolean existsByName(String name);
}
