package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.md4casestudy.model.Series;

import java.util.List;

public interface ISeriesRepo extends JpaRepository<Series, Long> {
    List<Series> findByNameContaining(String name);
}