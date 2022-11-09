package rikkei.academy.md4casestudy.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rikkei.academy.md4casestudy.dto.response.ResponseMessage;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.model.Category;
import rikkei.academy.md4casestudy.security.userprincipal.UserDetailsServiceIMPL;
import rikkei.academy.md4casestudy.service.category.ICategoryService;
import rikkei.academy.md4casestudy.service.user.IUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/manager/categories")
@CrossOrigin
public class CategoryApi {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private UserDetailsServiceIMPL userDetailsServiceIMPL;

    @GetMapping
    public ResponseEntity<?> getList() {
        Iterable<Category> listCategories = categoryService.findAll();
        return new ResponseEntity<>(listCategories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        if (categoryService.existsByName(category.getName())) {
            return new ResponseEntity<>(new ResponseMessage("category_invalid"), HttpStatus.OK);
        }
        category.setUser(currentUser);
        categoryService.save(category);
        return new ResponseEntity<>(new ResponseMessage("create success"), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> detailCategory(@PathVariable("id") Category category) {
        return category == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(category);
    }

    @PutMapping("{id} ")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category category1 = categoryService.findById(id);
        if (category1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        category1.setName(category.getName());
        categoryService.save(category1);
        return new ResponseEntity<>(new ResponseMessage("Update success!!!"), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);

        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("Delete Success!"), HttpStatus.OK);
    }
}
