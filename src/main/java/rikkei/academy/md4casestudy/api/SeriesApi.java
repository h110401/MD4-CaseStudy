package rikkei.academy.md4casestudy.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.md4casestudy.dto.request.SeriesDTO;
import rikkei.academy.md4casestudy.dto.response.ResponseMessage;
import rikkei.academy.md4casestudy.model.Series;
import rikkei.academy.md4casestudy.model.Video;
import rikkei.academy.md4casestudy.security.userprincipal.UserDetailsServiceIMPL;
import rikkei.academy.md4casestudy.service.category.ICategoryService;
import rikkei.academy.md4casestudy.service.country.ICountryService;
import rikkei.academy.md4casestudy.service.series.ISeriesService;
import rikkei.academy.md4casestudy.service.video.IVideoService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin
@RestController
@RequestMapping("/api/manager/series")
@RequiredArgsConstructor
public class SeriesApi {

    private final ISeriesService seriesService;
    private final ICategoryService categoryService;
    private final ICountryService countryService;
    private final IVideoService videoService;
    private final UserDetailsServiceIMPL userDetailsServiceIMPL;

    @GetMapping
    public ResponseEntity<?> getList() {
        List<Series> seriesList = seriesService.findAll();
        if (seriesList.isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(seriesList, OK);
    }

    @PostMapping
    public ResponseEntity<?> createSeries(
            @RequestBody SeriesDTO seriesDTO
    ) {
        Map<Integer, Video> videos = new HashMap<>();
        int i = 1;
        for (Long id : seriesDTO.getIdVideos()) {
            videos.put(i++, videoService.findById(id));
        }
        Series series = new Series(
                null,
                seriesDTO.getName(),
                seriesDTO.getPoster(),
                seriesDTO.getDescription(),
                Arrays.stream(seriesDTO.getIdCategories())
                        .map(categoryService::findById)
                        .collect(Collectors.toSet()),
                countryService.findById(seriesDTO.getIdCountry()),
                videos,
                userDetailsServiceIMPL.getCurrentUser()
        );
        seriesService.save(series);
        return ResponseEntity.ok(series);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Valid
            @PathVariable("id") Series series
    ) {
        if (series == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return ResponseEntity.ok(series);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> findByName(
            @PathVariable String search
    ) {
        return ResponseEntity.ok(seriesService.findByName(search));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editSeries(
            @PathVariable("id") Series series,
            @RequestBody SeriesDTO seriesDTO
    ) {
        if (series == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        series.setName(seriesDTO.getName());
        series.setPoster(seriesDTO.getPoster());
        series.setDescription(seriesDTO.getDescription());
        series.setCategory(Arrays.stream(seriesDTO.getIdCategories())
                .map(categoryService::findById).collect(Collectors.toSet()));
        series.setCountry(countryService.findById(seriesDTO.getIdCountry()));
        return ResponseEntity.ok(seriesService.save(series));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable("id") Series series
    ) {
        if (series == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        seriesService.deleteById(series.getId());
        return ResponseEntity.ok(new ResponseMessage("deleted"));
    }

    @PutMapping("{idSeries}/add/{idVideo}")
    public ResponseEntity<?> addVideoToSeries(
            @PathVariable Long idSeries,
            @PathVariable Long idVideo
    ) {
        seriesService.putVideoToSeries(idSeries, idVideo);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("{idSeries}/add/{idVideo}/{order}")
    public ResponseEntity<?> putVideoToSeries(
            @PathVariable Long idSeries,
            @PathVariable Long idVideo,
            @PathVariable int order
    ) {
        seriesService.putVideoToSeries(idSeries, idVideo, order);
        return new ResponseEntity<>(OK);
    }
}