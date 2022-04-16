package KKCH.StoreEverything.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping()
    public AppUser getUser(@RequestParam Long id){
        return appUserService.get(id);
    }

    @GetMapping("/all")
    public List<AppUser> getUsers() {
        return appUserService.getAll();
    }

    @PostMapping()
    public AppUser createUser(@RequestBody AppUserDto userDto) {
        return appUserService.update(userDto);
    }

    @DeleteMapping()
    public AppUser deleteUser(@RequestBody AppUserDto userDto){
        return appUserService.delete(userDto);
    }
}
