package KKCH.StoreEverything.AppUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private AppUserService userService;

    @GetMapping("")
    public String getList(Model model){
        model.addAttribute("users",userService.getAll());
        return "user-list";
    }

    @GetMapping("/{id}")
    public String getUser (@PathVariable Long id, Model model) {
        AppUser user = userService.get(id);
        user.setPassword("");
        model.addAttribute("user",user);
        return "user-edit";
    }
}