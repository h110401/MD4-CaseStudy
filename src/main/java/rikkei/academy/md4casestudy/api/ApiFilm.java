package rikkei.academy.md4casestudy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.md4casestudy.dto.response.ResponseMessage;
import rikkei.academy.md4casestudy.model.*;
import rikkei.academy.md4casestudy.security.userprincipal.UserDetailsServiceIMPL;
import rikkei.academy.md4casestudy.service.film.IFilmService;

import javax.validation.Valid;
@CrossOrigin
@RestController
@RequestMapping("/api/manager/films")
public class ApiFilm {
    @Autowired
    private IFilmService filmService;
    @Autowired
    private UserDetailsServiceIMPL userDetailsServiceIMPL;
    @GetMapping
    public ResponseEntity<?> showListFilm(){
        Iterable<Film> listFilm = filmService.findAll();
        return new ResponseEntity<>(listFilm, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createFilm(@Valid @RequestBody Film film){
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        if (filmService.existsByName(film.getName())){
            return new ResponseEntity<>(new ResponseMessage("film_invalid"),HttpStatus.OK);
        }
        film.setUser(currentUser);
        filmService.save(film);
        return new ResponseEntity<>(new ResponseMessage("create success"),HttpStatus.OK);
    }

   @PutMapping("/{id}")
   public ResponseEntity<?> editFilm(@PathVariable Long id , @RequestBody Film film){
        Film film1 = filmService.findById(id);
        if (film1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        film1.setName(film.getName());
        filmService.save(film1);
        return new ResponseEntity<>(new ResponseMessage(" edit success"),HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable Long id){
        Film film = filmService.findById(id);
        if (film==null){
            return new ResponseEntity<>(new ResponseMessage("Film does not exist"),HttpStatus.NOT_FOUND);
        }
        filmService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("delete success"),HttpStatus.OK);
   }

   @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Long id){
        return new ResponseEntity<>(filmService.findById(id),HttpStatus.OK);
   }

}
