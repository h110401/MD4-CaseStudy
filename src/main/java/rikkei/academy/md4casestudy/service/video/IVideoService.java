package rikkei.academy.md4casestudy.service.video;

import rikkei.academy.md4casestudy.model.Video;
import rikkei.academy.md4casestudy.service.IGenericService;

public interface IVideoService extends IGenericService<Video> {
    Boolean existsByName(String name);
}
