package KKCH.StoreEverything.Information;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/information")
public class InformationController {
    private InformationService informationService;
    private ModelMapper modelMapper;

    @GetMapping("/add")
    public String showSignUpForm() {
        return "add-information";
    }
    @PostMapping("/add")
    public String addInforamtion(@Valid InformationDto inforamtion, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-information";
        }
        InformationOrm informationOrm = modelMapper.map(inforamtion,InformationOrm.class);
        return "redirect:/index";
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