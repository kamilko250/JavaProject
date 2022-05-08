package KKCH.StoreEverything.Security;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserRepository;
import KKCH.StoreEverything.AppUser.CustomUser;
import KKCH.StoreEverything.Role.Privilege.Privilege;
import KKCH.StoreEverything.Role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    private AppUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByLogin(String login) throws Exception {
        Optional<AppUser> optUser = userRepository.findByLogin(login);
        if (optUser.isEmpty()) {
            optUser = userRepository.findByName(login);//przy logowaniu wewnątrz używane jest loadUserByUsername, więc w ten sposób jest "obejście"
            if(optUser.isEmpty()) {
                throw new Exception(String.format("Name: '%s' not found", login));
            }
        }
        AppUser user = optUser.get();
        //Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        /*for (UserRole role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }*/

        return new CustomUser(
                user.getId(),
                user.getName(),
                user.getPassword(),
                getAuthorities(user.getRoles()));
    }

    //default implementation
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return loadUserByLogin(username);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Optional<AppUser> user = userRepository.findByName(username);
        if (!user.isPresent()) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : user.get().getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new CustomUser(user.get().getId(),user.get().getName(), user.get().getPassword(), grantedAuthorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Set<UserRole> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Set<UserRole> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (UserRole role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}