package rikkei.academy.md4casestudy.service.country;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.md4casestudy.model.Country;
import rikkei.academy.md4casestudy.repo.ICountryRepository;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class CountryServiceIMPL implements ICountryService{
    @Autowired
    private ICountryRepository countryRepository;
    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Page<Country> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public void deleteById(Long id) {
        countryRepository.deleteById(id);

    }

    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsByName(String name) {
        return countryRepository.existsByName(name);
    }
}
