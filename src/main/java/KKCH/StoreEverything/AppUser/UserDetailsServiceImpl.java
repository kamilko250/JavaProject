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
    public UserDetails loadUserByLogin(String login) {
        Optional<AppUser> user = userRepository.findByLogin(login);
        if (!user.isPresent()) throw new UsernameNotFoundException(login);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : user.get().getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(), grantedAuthorities);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByLogin(username);
        if (!user.isPresent()) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : user.get().getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(), grantedAuthorities);
    }
}