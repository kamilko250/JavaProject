package KKCH.StoreEverything;


import KKCH.StoreEverything.AppUser.AppUserService;
import KKCH.StoreEverything.AppUser.CustomUser;
import KKCH.StoreEverything.Information.InformationDto;
import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Information.InformationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class GeneralController {
    @Autowired
    private AppUserService service;
    private InformationService informationService;
    private ModelMapper modelMapper;

    @GetMapping("")
    public String showInformationForm (Authentication auth, Model model) {
        if (auth != null) {
            CustomUser user = (CustomUser) auth.getPrincipal();
            model.addAttribute("userdata", service.get(user.getId()));
        }
        return "index";
    }

    @GetMapping("/share/{id}")
    public String showSharedInformation (
            @PathVariable("id")
                    Long id, Model
                    model
    ) {
        // valid if user has occess or is link public

        Optional<InformationOrm> informationOrm = informationService.getById(id);
        if (informationOrm.isPresent())
            model.addAttribute("information", modelMapper.map(informationOrm.get(), InformationDto.class));

        return "shared_information";
    }

    @Autowired
    public void setInformationService (InformationService informationService) {
        this.informationService = informationService;
    }

    @Autowired
    public void setModelMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


}