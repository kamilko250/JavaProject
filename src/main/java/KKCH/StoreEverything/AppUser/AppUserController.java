package KKCH.StoreEverything.AppUser;

import KKCH.StoreEverything.Role.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class AppUserController {

    private final AppUserService appUserService;
    private final UserRoleService roleService;
    private ModelMapper modelMapper;

    @Autowired
    public AppUserController(AppUserService appUserService, UserRoleService roleService) {
        this.appUserService = appUserService;
        this.roleService = roleService;
    }

    @GetMapping()
    public AppUser getUser (@RequestParam Long id) {
        return appUserService.get(id);
    }

    @GetMapping("/all")
    public List<AppUser> getUsers () {
        return appUserService.getAll();
    }

    @PutMapping("/{id}")
    public AppUser updateUser (@Valid @RequestBody AppUserEditDto userDto, @PathVariable Long id) throws Exception {
        if (!userDto.getPassword()
                .equals("")) {
            var pass = userDto.getPassword();
            if (pass.equals("") || (pass.length() <5))
                throw new ValidationException("Password is not valid");
        }
        userDto.setId(id);
        return appUserService.update(modelMapper.map(userDto, AppUserDto.class));
    }


    @DeleteMapping()
    public AppUser deleteUser (@RequestBody AppUserDto userDto) {
        return appUserService.delete(userDto);
    }

    @PostMapping("/register")
    public AppUser register (@Valid @RequestBody AppUserDto userDto) throws Exception {
        return appUserService.register(userDto);
    }

    @PostMapping("/login")
    public void login (@Valid @RequestBody UserLoginData loginData) throws Exception {
        appUserService.login(loginData.getLogin(), loginData.getPassword());
    }

    @GetMapping("/logout")
    public void logout () {
        appUserService.logout();
    }

    @PostMapping("/addRole")
    public void addToRole(@RequestParam Long userId, @RequestParam String roleName){ roleService.addToRole(userId, roleName);}

    @PostMapping("/removeRole")
    public void removeFromRole(@RequestParam Long userId, @RequestParam String roleName){ roleService.removeFromRole(userId, roleName);}

    @Autowired
    public void setModelMapper (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
