package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public InformationOrm update (InformationOrm information) {
        CategoryOrm category = categoryService.findByName(information.getCategory().getName());
        if (category != null)
            information.setCategory(category);

        informationRepository.save(information);

        return information;
    }

    public Optional<InformationOrm> getById (Long id){
        return informationRepository.findById(id);
    }

    public java.util.List<InformationOrm> getAll(){
        return informationRepository.findAll();
    }

    public void DeleteById(Long id){
        Optional<InformationOrm> informationOrm = informationRepository.findById(id);
        if(informationOrm.isPresent())
            informationRepository.delete(informationOrm.get());
    }

    public List<InformationOrm> getSharedInformationForUser(AppUser user){
        return informationRepository.findAllByAllowedUsers(user);
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