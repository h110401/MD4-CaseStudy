package rikkei.academy.md4casestudy.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.md4casestudy.model.Category;
import rikkei.academy.md4casestudy.model.Film;
import rikkei.academy.md4casestudy.repo.IFilmRepo;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmServiceIMPL implements IFilmService {
    @Autowired
    private IFilmRepo filmRepo;
    @Override
    public List<Film> findAll() {
        return filmRepo.findAll();
    }

    @Override
    public Page<Film> findAll(Pageable pageable) {
        return filmRepo.findAll(pageable);
    }

    @Override
    public Film save(Film film) {
        return filmRepo.save(film);
    }

    @Override
    public void deleteById(Long id) {
        filmRepo.deleteById(id);

    }

    @Override
    public Film findById(Long id) {
        return filmRepo.findById(id).orElse(null);
    }


    @Override
    public Boolean existsByName(String name) {
        return filmRepo.existsByName(name);
    }




}
