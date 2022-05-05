package KKCH.StoreEverything.AppUser;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class AppUserService implements UserService {
    //---
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public AppUserService (AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public AppUser get (Long id) {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isPresent())
            return appUser.get();
        return null;
    }

    public List<AppUser> getAll () {
        return appUserRepository.findAll();
    }

    public AppUser update (AppUserDto userDto) {
        Optional<AppUser> appUser = appUserRepository.findById(userDto.getId());
        AppUser user = null;
        if (appUser.isPresent()) {
            user = appUser.get();
        }
        AppUser newAppUser = new AppUser(
                userDto.getId(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getEmail(),
                null,
                userDto.getAge(),
                userDto.getLogin()
        );
        if (userDto.getPassword().equals("")) {
            newAppUser.setPassword(user.getPassword());
        } else {
            encodePassword(newAppUser, userDto);
        }

        return appUserRepository.save(newAppUser);
    }

    public AppUser delete (AppUserDto userDto) {
        Optional<AppUser> appUser = appUserRepository.findById(userDto.getId());
        if (appUser.isPresent()) {
            appUserRepository.delete(appUser.get());
            return appUser.get();
        }
        return null;
    }

    //update/register są bardzo podobne, jedno do wywalenia pójdzie NIE WYWALAJ UPDATE!!!
    @Override
    public AppUser register (AppUserDto userDto) throws Exception {
        //if(checkIfUserExist(userDto.getId())){
        //    throw new Exception("User already exists");
        //}
        AppUser user = new AppUser();
        BeanUtils.copyProperties(userDto, user);
        encodePassword(user, userDto);
        appUserRepository.save(user);

        return user;
    }

    @Override
    public void login (String login, String password) throws Exception {
        Optional<AppUser> appUser = appUserRepository.findByLogin(login);
        if (appUser.isEmpty()) {
            throw new Exception("user not found");
        }
        UserDetails userDetails = userDetailsService.loadUserByLogin(login);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    public void logout () {
        SecurityContextHolder.getContext()
                .setAuthentication(null);
    }

    public AppUser getCurrentUser () {
        //SecurityContextHolder.getContext().getAuthentication().getName();
        return (AppUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getDetails();
    }

    @Override
    public boolean checkIfUserExist (Long id) {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        return appUser.isPresent();
    }

    @Override
    public Optional<AppUser> getUserById (Long id) throws Exception {
        return appUserRepository.findById(id);
    }

    private void encodePassword (AppUser user, AppUserDto userdto) {
        user.setPassword(passwordEncoder.encode(userdto.getPassword()));
    }
}