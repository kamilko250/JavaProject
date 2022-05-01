package KKCH.StoreEverything;


import KKCH.StoreEverything.Category.CategoryDto;
import KKCH.StoreEverything.Information.InformationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/login")
public class AuthController {

    @GetMapping("")
    public String showInformationForm (Model model) {
        return "login";
    }

}