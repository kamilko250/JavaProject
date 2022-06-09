package KKCH.StoreEverything.AppUser;

import KKCH.StoreEverything.Role.UserRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class AppUserTests {
    static UserRole admin = null;
    static UserRole basicUser = null;

    @BeforeAll
    public static void CreateRoles(){
        admin = new UserRole("admin_role", "Admin");
        basicUser = new UserRole("user_role", "User");
    }

    @AfterAll
    public static void Cleanup(){
        admin = null;
        basicUser = null;
    }

    @Test
    public void AddRoleToUser(){
        AppUser user = new AppUser();
        Set<UserRole> roles = new HashSet<>();
        roles.add(admin);
        roles.add(basicUser);

        user.setRoles(roles);

        assert user.getRoles().size() == 2;
    }

    @Test
    public void RemoveRoleFromUser(){
        AppUser user = new AppUser();
        Set<UserRole> roles = new HashSet<>();
        roles.add(admin);
        roles.add(basicUser);
        user.setRoles(roles);

        user.getRoles().remove(admin);

        assert user.getRoles().size() == 1;
    }
}
