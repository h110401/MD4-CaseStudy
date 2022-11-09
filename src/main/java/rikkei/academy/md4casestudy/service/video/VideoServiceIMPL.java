package rikkei.academy.md4casestudy.service.video;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.md4casestudy.model.Video;
import rikkei.academy.md4casestudy.repo.IVideoRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoServiceIMPL implements IVideoService {
    @Autowired
    private IVideoRepository videoRepository;

    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Page<Video> findAll(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void deleteById(Long id) {
        videoRepository.deleteById(id);

    }

    @Override
    public Video findById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsByName(String name) {
        return videoRepository.existsByName(name);
    }

    @Override
    public List<Video> findByName(String name) {
        return videoRepository.findByNameContaining(name);
    }
}
