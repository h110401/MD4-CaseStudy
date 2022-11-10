package rikkei.academy.md4casestudy.service.series;

import rikkei.academy.md4casestudy.model.Series;
import rikkei.academy.md4casestudy.service.IGenericService;

import java.util.List;

public interface ISeriesService extends IGenericService<Series> {
    void putVideoToSeries(Long idSeries, Long idVideo, int order);

    void putVideoToSeries(Long idSeries, Long idVideo);

    List<Series> findByName(String name);
}
