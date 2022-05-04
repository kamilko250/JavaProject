package KKCH.StoreEverything.AppUser;

import KKCH.StoreEverything.Role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    private AppUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByLogin(String login) throws Exception {
        Optional<AppUser> user = userRepository.findByLogin(login);
        if (!user.isPresent()) throw new Exception(String.format("Login name: '%s' not found", login));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : user.get().getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new CustomUser(user.get()
                                      .getId(), user.get()
                                      .getName(), user.get()
                                      .getPassword(), grantedAuthorities);
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
}