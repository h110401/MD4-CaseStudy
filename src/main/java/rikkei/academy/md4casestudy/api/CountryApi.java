package rikkei.academy.md4casestudy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.md4casestudy.dto.response.ResponseMessage;
import rikkei.academy.md4casestudy.model.Country;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.security.userprincipal.UserDetailsServiceIMPL;
import rikkei.academy.md4casestudy.service.country.ICountryService;
import rikkei.academy.md4casestudy.service.user.IUserService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/manager/countries")
public class CountryApi {
    @Autowired
    private ICountryService countryService;
    @Autowired
    private UserDetailsServiceIMPL userDetailsServiceIMPL;
    @GetMapping
    public ResponseEntity<?>showListCountry(){
        Iterable<Country>listCountry = countryService.findAll();
        return new ResponseEntity<>(listCountry, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?>createCountry(@Valid @RequestBody Country country){
        User currenUser = userDetailsServiceIMPL.getCurrentUser();
        if (countryService.existsByName(country.getName())){
            return new ResponseEntity<>(new ResponseMessage("country_invalid"),HttpStatus.OK);
        }
        country.setUser(currenUser);
        countryService.save(country);
        return new ResponseEntity<>(new ResponseMessage("create_success!"),HttpStatus.OK);
    }
   @PutMapping("/{id}")
    public ResponseEntity<?>UpdateCountry(@PathVariable Long id,@RequestBody Country country){
       Country country1 = countryService.findById(id);
       if (country1 == null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       country1.setName(country.getName());
       countryService.save(country1);
       return new ResponseEntity<>(new ResponseMessage("update_success!"),HttpStatus.OK);
   }
   @DeleteMapping("/{id}")
    public ResponseEntity<?>DeleteCountry(@PathVariable Long id){
        Country country = countryService.findById(id);
        if (country == null){
            return new ResponseEntity<>(new ResponseMessage("country does not exist!!"), HttpStatus.NOT_FOUND);
        }
        countryService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("delete_success!"),HttpStatus.OK);
   }
   @GetMapping("/{id}")
    public ResponseEntity<?>getCountryById(@PathVariable Long id){
        return new ResponseEntity<>(countryService.findById(id),HttpStatus.OK);
   }




}
