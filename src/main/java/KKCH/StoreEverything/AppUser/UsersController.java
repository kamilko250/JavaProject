package KKCH.StoreEverything.AppUser;


import KKCH.StoreEverything.Enums.LoggerEnum;
import KKCH.StoreEverything.Utils.KKCHLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final Logger log = KKCHLogger.getLoggerWithSetLevel(LoggerEnum.USER, Level.INFO);
    @Autowired
    private AppUserService userService;

    @GetMapping("")
    public String getList(Model model){
        model.addAttribute("users",userService.getAll());
        log.info("Fetched users");
    return "user-list";
    }

    @GetMapping("/{id}")
    public String getUser (@PathVariable Long id, Model model) {
        AppUser user = userService.get(id);
        user.setPassword("");
        model.addAttribute("user",user);
        log.info("Fetched user with id " + id);
        return "user-edit";
    }
}