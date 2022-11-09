package rikkei.academy.md4casestudy.service.video;

import rikkei.academy.md4casestudy.model.Video;
import rikkei.academy.md4casestudy.service.IGenericService;

import java.util.List;

public interface IVideoService extends IGenericService<Video> {
    Boolean existsByName(String name);

    List<Video> findByName(String name);
}
