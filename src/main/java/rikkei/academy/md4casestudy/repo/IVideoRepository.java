package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.md4casestudy.model.Video;

public interface IVideoRepository extends JpaRepository<Video,Long> {
    Boolean existsByName(String name);
}
