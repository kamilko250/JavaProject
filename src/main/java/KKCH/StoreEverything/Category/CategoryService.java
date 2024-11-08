package KKCH.StoreEverything.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public List<CategoryOrm> getAll(){
        return this.repository.findAll();
    }

    public CategoryOrm findByName(String name){
        return repository.findOneByName(name);
    }
}