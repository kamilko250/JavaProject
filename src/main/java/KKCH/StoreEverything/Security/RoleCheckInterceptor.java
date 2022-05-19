package KKCH.StoreEverything.Security;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserRepository;
import KKCH.StoreEverything.AppUser.CustomUser;
import KKCH.StoreEverything.Role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RoleCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private AppUserRepository userRepository;

    public static Set<Long> userIdsWithUpdatedRoles = new HashSet<>();//list of user ids

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            CustomUser current = (CustomUser) auth.getPrincipal();
            Long userId = current.getId();
            AppUser user = userRepository.findById(userId).get();
            if (userIdsWithUpdatedRoles.contains(userId)) {
                updateRoles(auth, user);
                userIdsWithUpdatedRoles.remove(userId);
            }

        } catch (Exception e) {
            throw new Exception("Failed to update user role");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    private void updateRoles(Authentication auth, AppUser user) {
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
        for (UserRole role : user.getRoles()) {
            updatedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
                updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
