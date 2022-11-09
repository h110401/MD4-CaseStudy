package rikkei.academy.md4casestudy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDTO {
    private String name;
    private String poster;
    private String description;
    private Long[] idCategories;
    private Long idCountry;
    private Long[] idVideos;
}
