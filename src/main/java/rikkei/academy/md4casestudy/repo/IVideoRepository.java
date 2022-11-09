package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rikkei.academy.md4casestudy.model.Video;

import java.util.List;

public interface IVideoRepository extends JpaRepository<Video, Long> {
    Boolean existsByName(String name);

    @Query(
            nativeQuery = true,
            value = "select * from videos v where v.name like concat('%',?1,'%') and v.id not in (select videos_id as id from series_videos)"
    )
    List<Video> findByNameContaining(String name);
}
