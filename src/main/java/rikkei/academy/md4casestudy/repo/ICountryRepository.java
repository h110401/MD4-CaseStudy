package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.md4casestudy.model.Country;

public interface ICountryRepository extends JpaRepository<Country,Long> {
    Boolean existsByName(String name);

}
