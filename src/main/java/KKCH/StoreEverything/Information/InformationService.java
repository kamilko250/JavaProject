package KKCH.StoreEverything.Information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InformationService {

    private InformationRepository informationRepository;

    @Transactional
    public InformationOrm create (InformationOrm information){
        informationRepository.save(information);

        return information;
    }

    @Autowired
    public void setInformationRepository (InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }
}