package KKCH.StoreEverything.Role;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class UserRoleService {
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
