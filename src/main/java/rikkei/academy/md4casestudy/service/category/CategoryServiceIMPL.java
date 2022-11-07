package rikkei.academy.md4casestudy.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rikkei.academy.md4casestudy.model.Category;
import rikkei.academy.md4casestudy.repo.ICateRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceIMPL implements ICategoryService {
    @Autowired

    private ICateRepo cateRepo;

    @Override
    public List<Category> findAll() {
        return cateRepo.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return cateRepo.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        return cateRepo.save(category);
    }

    @Override
    public void deleteById(Long id) {
        cateRepo.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        return cateRepo.findById(id).orElse(null);
    }


    @Override
    public Boolean existsByName(String name) {
        return cateRepo.existsByName(name);
    }
}
