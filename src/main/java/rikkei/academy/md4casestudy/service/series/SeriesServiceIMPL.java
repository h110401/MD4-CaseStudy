package rikkei.academy.md4casestudy.service.series;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.md4casestudy.model.Series;
import rikkei.academy.md4casestudy.model.Video;
import rikkei.academy.md4casestudy.repo.ISeriesRepo;
import rikkei.academy.md4casestudy.repo.IVideoRepository;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SeriesServiceIMPL implements ISeriesService {

    private final ISeriesRepo seriesRepo;
    private final IVideoRepository videoRepository;

    @Override
    public List<Series> findAll() {
        return seriesRepo.findAll();
    }

    @Override
    public Page<Series> findAll(Pageable pageable) {
        return seriesRepo.findAll(pageable);
    }

    @Override
    public Series save(Series series) {
        return seriesRepo.save(series);
    }

    @Override
    public void deleteById(Long id) {
        seriesRepo.deleteById(id);
    }

    @Override
    public Series findById(Long id) {
        return seriesRepo.findById(id).orElse(null);
    }

    @Override
    public void putVideoToSeries(Long idSeries, Long idVideo, int order) {
        Series series = seriesRepo.findById(idSeries).orElse(null);
        Video video = videoRepository.findById(idVideo).orElse(null);
        if (series != null && video != null) {
            series.getVideos().put(order, video);
        }
    }

    @Override
    public void putVideoToSeries(Long idSeries, Long idVideo) {
        Series series = seriesRepo.findById(idSeries).orElse(null);
        Video video = videoRepository.findById(idVideo).orElse(null);
        if (series != null && video != null) {
            Map<Integer, Video> videos = series.getVideos();
            videos.put(videos.size() + 1, video);
        }
    }

    @Override
    public List<Series> findByName(String name) {
        return seriesRepo.findByNameContaining(name);
    }


}
