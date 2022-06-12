package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Category.CategoryService;
import KKCH.StoreEverything.Enums.LoggerEnum;
import KKCH.StoreEverything.Utils.KKCHLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class InformationService {
    private final Logger log = KKCHLogger.getLogger(LoggerEnum.INFORMATION);
    private CategoryService categoryService;
    private InformationRepository informationRepository;

    @Transactional
    public InformationOrm create (InformationOrm information){
        CategoryOrm category = categoryService.findByName(information.getCategory().getName());
        if (category != null) {
            information.setCategory(category);
            log.finest("Correctly set category for information with id " + information.getId());
        }else{
            log.warning("Failed to find category");
        }

        informationRepository.save(information);
        log.finer("Saved information with id " + information.getId());

        return information;
    }

    @Transactional
    public InformationOrm update (InformationOrm information) {
        CategoryOrm category = categoryService.findByName(information.getCategory().getName());
        if (category != null) {
            information.setCategory(category);
            log.finest("Correctly set category for information with id " + information.getId());
        }else{
            log.warning("Failed to find category with id " + category.getId());
        }

        informationRepository.save(information);
        log.finer("Saved information with id " + information.getId());

        return information;
    }

    public Optional<InformationOrm> getById (Long id){
        return informationRepository.findById(id);
    }

    public java.util.List<InformationOrm> getAll (AppUser user) {
        return informationRepository.findAllByAppUser(user);
    }

    public java.util.List<InformationOrm> getAll () {
        return informationRepository.findAll();
    }

    public void DeleteById(Long id){
        Optional<InformationOrm> informationOrm = informationRepository.findById(id);
        if(informationOrm.isPresent()) {
            informationRepository.delete(informationOrm.get());
            log.fine("Deleted information with id  " + id);
        }else{
            log.severe("Failed to find information with id " + id);
        }
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