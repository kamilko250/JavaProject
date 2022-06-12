package KKCH.StoreEverything.AppUser;

import KKCH.StoreEverything.EmailSender.MailService;
import KKCH.StoreEverything.Enums.LoggerEnum;
import KKCH.StoreEverything.Role.UserRole;
import KKCH.StoreEverything.Role.UserRoleRepository;
import KKCH.StoreEverything.Security.CustomUserDetailsService;
import KKCH.StoreEverything.Utils.KKCHLogger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service("userService")
public class AppUserService implements UserService {
    private final Logger log = KKCHLogger.getLogger(LoggerEnum.USER);
    //---
    private final AppUserRepository appUserRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;


    @Autowired
    public AppUserService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            UserRoleRepository roleRepository,
            CustomUserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            MailService mailService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.mailService = mailService;
    }

    public AppUser get (Long id) {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        if (appUser.isPresent()) {
            log.finer(String.format("User with id %d found", id));
            return appUser.get();
        }
        log.warning(String.format("Didnt find user with id %d", id));
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
        newAppUser.setRoles(appUser.get()
                                    .getRoles());
        log.finest(String.format("Set user %s roles", newAppUser.getLogin()));
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
            log.info("Removed user " + appUser.get().getLogin());
            return appUser.get();
        }
        return null;
    }

    @Override
    public AppUser register (AppUserDto userDto) throws Exception {
        AppUser user = new AppUser();
        BeanUtils.copyProperties(userDto, user);
        encodePassword(user, userDto);
        UserRole role = roleRepository.findByName("ROLE_USER").get();
        Set<UserRole> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);//add to "user" role by default
        if(user.getRoles().size() == 1){
            log.finest(String.format("Correctly set user %s roles", user.getLogin()));
        }else{
            log.info(String.format("Failed to correctly set roles for user %s", user.getLogin()));
        }
        appUserRepository.save(user);

        mailService.sendMail(user.getEmail(), "Registration", "Link to registration", false);
        log.fine(String.format("Sent registration email to user %s having email %s", user.getLogin(), user.getEmail()));
        log.info("New user registered, login is " + user.getLogin());

        return user;
    }

    @Override
    public void login (String login, String password) throws Exception {
        Optional<AppUser> appUser = appUserRepository.findByLogin(login);
        if (appUser.isEmpty()) {
            log.warning(String.format("User %s not found!", login));
            throw new Exception("user not found");
        }
        UserDetails userDetails = userDetailsService.loadUserByLogin(login);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
        }else{
            log.severe(String.format("Failed to set authentication for user %s", login));
            return;
        }
        log.info(String.format("User %s logged in", login));
    }

    public void logout () {
        SecurityContextHolder.getContext()
                .setAuthentication(null);
    }

    public AppUserDto getCurrentUser () {
        //SecurityContextHolder.getContext().getAuthentication().getName();
        String name = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Optional<AppUser> optUser = appUserRepository.findByName(name);
        if(optUser.isEmpty()) {
            log.severe(String.format("Failed to get current user %s", name));
            return null;
        };
        AppUserDto userDto = new AppUserDto();
        BeanUtils.copyProperties(optUser.get(), userDto);
        log.fine(String.format("Found user %s", name));

        return userDto;
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
        log.finer(String.format("Encoded password for user %s", user.getLogin()));
    }
}
