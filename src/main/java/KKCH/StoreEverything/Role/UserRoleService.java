package KKCH.StoreEverything.Role;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserRepository;
import KKCH.StoreEverything.Enums.LoggerEnum;
import KKCH.StoreEverything.Security.RoleCheckInterceptor;
import KKCH.StoreEverything.Utils.KKCHLogger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class UserRoleService {
    private final Logger log = KKCHLogger.getLogger(LoggerEnum.ROLE);
    private UserRoleRepository roleRepository;
    private AppUserRepository userRepository;

    private AppUser user;
    private UserRole role;
    //zakładamy, że role są predefiniowane i nigdy się nie zmienią

    public void addToRole(Long userId, String roleName){
        Optional<AppUser> optUser = userRepository.findById(userId);
        Optional<UserRole> optRole = roleRepository.findByName(roleName);

        if(optRole.isPresent() && optUser.isPresent()){
            role = optRole.get();
            user = optUser.get();
        }else{
            return;
        }
        Set<UserRole> roles = user.getRoles();

        if(roles.add(role)){
            userRepository.save(user);
        }

        RoleCheckInterceptor.userIdsWithUpdatedRoles.add(user.getId());
        log.info(String.format("Added role %s to user %s", role.getName(), user.getLogin()));
    }

    public void removeFromRole(Long userId, String roleName){
        Optional<AppUser> optUser = userRepository.findById(userId);
        Optional<UserRole> optRole = roleRepository.findByName(roleName);

        if(optRole.isPresent() && optUser.isPresent()){
            role = optRole.get();
            user = optUser.get();
        }else{
            return;
        }
        Set<UserRole> roles = user.getRoles();

        if(roles.remove(role)){
            userRepository.save(user);
        }

        RoleCheckInterceptor.userIdsWithUpdatedRoles.add(user.getId());
        log.info(String.format("Removed role %s from user %s", role.getName(), user.getLogin()));
    }

    @Autowired
    public void setUserRepository(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
