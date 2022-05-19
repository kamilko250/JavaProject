package KKCH.StoreEverything;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserRepository;
import KKCH.StoreEverything.Role.Privilege.Privilege;
import KKCH.StoreEverything.Role.Privilege.PrivilegeRepository;
import KKCH.StoreEverything.Role.UserRole;
import KKCH.StoreEverything.Role.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        Set<Privilege> adminPrivileges = new HashSet<>(
                Arrays.asList(readPrivilege, writePrivilege)
        );
        Set<Privilege> userPrivileges = new HashSet<>(
                Arrays.asList(readPrivilege, writePrivilege)
        );
        Set<Privilege> limitedUserPrivileges = new HashSet<>(
                Arrays.asList(readPrivilege)
        );
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges, "Admin");
        createRoleIfNotFound("ROLE_USER", userPrivileges, "User");
        createRoleIfNotFound("ROLE_LIMITEDUSER", limitedUserPrivileges, "Limited user");

        UserRole adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        AppUser admin = new AppUser(
                "Admin",
                "Adminn",
                "admin@test.t",
                passwordEncoder.encode("admin"),
                22,
                "admin",
                new HashSet<>(Arrays.asList(adminRole))
        );
        userRepository.save(admin);

        AppUser user = new AppUser(
                "User",
                "User",
                "user@test.t",
                passwordEncoder.encode("usera"),
                22,
                "usera",
                new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER")
                                                    .get()))
        );
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Optional<Privilege> optPrivilege = privilegeRepository.findByName(name);
        Privilege privilege;
        if (optPrivilege.isEmpty()) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }else{
            privilege = optPrivilege.get();
        }

        return privilege;
    }

    @Transactional
    UserRole createRoleIfNotFound(String name, Set<Privilege> privileges,String title) {

        Optional<UserRole> optRole = roleRepository.findByName(name);
        UserRole role;
        if (optRole.isEmpty()) {
            role = new UserRole(name,title);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }else{
            role = optRole.get();
        }

        return role;
    }
}