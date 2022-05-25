package KKCH.StoreEverything;


import KKCH.StoreEverything.AppUser.AppUser;
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
            var appuser= service.get(user.getId());
            model.addAttribute("userdata",appuser);
            var informationList = informationService.getSharedInformationForUser(appuser);
            model.addAttribute("InformationList",informationList);

        }
        return "index";
    }

    @GetMapping("/share/{id}")
    public String showSharedInformation (
            Authentication auth,
            @PathVariable("id")
                    Long id, Model
                    model
    ) {
        Optional<InformationOrm> informationOrm = informationService.getById(id);

        CustomUser user = null;
        AppUser allowed = null;
        if (auth != null) {
            user = (CustomUser) auth.getPrincipal();
            allowed = service.get(user.getId());
        } else
            return "redirect:/";
        if (informationOrm.isPresent()) {
            InformationOrm information = informationOrm.get();
            if(information.isPublic() || information.isUserAllowed(allowed))
            {
                model.addAttribute("information", modelMapper.map(informationOrm.get(), InformationDto.class));
                return "shared_information";
            } else
                 return "redirect:/";
        }
        return "redirect:/";
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