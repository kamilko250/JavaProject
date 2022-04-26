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
//do usunięcia
    @PostMapping()
    public AppUser createUser(@RequestBody AppUserDto userDto) {
        return appUserService.update(userDto);
    }

    @DeleteMapping()
    public AppUser deleteUser(@RequestBody AppUserDto userDto){
        return appUserService.delete(userDto);
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUserDto userDto) throws Exception {
        return appUserService.register(userDto);
    }

    @GetMapping("/login")
    public void login(@RequestBody AppUserDto userDto) throws Exception {
        appUserService.login(userDto.getName(), userDto.getPassword());
    }

    @GetMapping("/logout")
    public void logout() {
        appUserService.logout();
    }
}
