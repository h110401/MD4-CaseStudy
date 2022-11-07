package rikkei.academy.md4casestudy.service.country;

import rikkei.academy.md4casestudy.model.Country;
import rikkei.academy.md4casestudy.service.IGenericService;

public interface ICountryService extends IGenericService<Country> {
    Boolean existsByName(String name);
}
