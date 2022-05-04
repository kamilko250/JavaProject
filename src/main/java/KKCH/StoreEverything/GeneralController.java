package KKCH.StoreEverything;


import KKCH.StoreEverything.AppUser.AppUserService;
import KKCH.StoreEverything.AppUser.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GeneralController {
    @Autowired
    private AppUserService service;

    @GetMapping("")
    public String showInformationForm (Authentication auth, Model model) {
        if (auth != null) {
            CustomUser user = (CustomUser) auth.getPrincipal();
            model.addAttribute("userdata", service.get(user.getId()));
        }
        return "index";
    }

}