package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InformationService {
    private CategoryService categoryService;
    private InformationRepository informationRepository;

    @Transactional
    public InformationOrm create (InformationOrm information){
        CategoryOrm category = categoryService.findByName(information.getCategory().getName());
        if (category != null)
            information.setCategory(category);

        informationRepository.save(information);

        return information;
    }

    @Autowired
    public void setInformationRepository (InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    @Autowired
    public void setCategoryService (CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}