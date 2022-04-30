package KKCH.StoreEverything;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserDto;
import KKCH.StoreEverything.AppUser.AppUserRepository;
import KKCH.StoreEverything.AppUser.AppUserService;
import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Information.InformationService;
import KKCH.StoreEverything.Role.UserRole;
import KKCH.StoreEverything.Role.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Configuration
public class StoreEverythinkConfiguration {
    //@Transactional
    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository appUserRepository, UserRoleRepository userRoleRepository, InformationService repository, AppUserService appUserService) {
        return args -> {
            UserRole testRole = new UserRole("user");
            UserRole testRole2 = new UserRole("admin");
            userRoleRepository.save(testRole);
            userRoleRepository.save(testRole2);
            Set<UserRole> roles = new HashSet<>();
            roles.add(testRole);
            roles.add(testRole2);

            AppUser kamil = new AppUser(
                    "Kamil",
                    "Kamil",
                    "Kamil",
                    "Kamil",
                    22,
                    "Kamil"
            );
            kamil.setRoles(roles);
            appUserRepository.save(kamil);
            Optional<AppUser> t = appUserRepository.findByName("Kamil");
            if(t.isPresent()){
                Set<UserRole> kRoles = (Set<UserRole>) t.get().getRoles();
                kRoles.forEach(System.out::println);
            }
            AppUserDto us = new AppUserDto();
            us.setAge(22);
            us.setEmail("abc@def.gh");
            us.setPassword("aaaaa");
            us.setName("ak");
            us.setSurname("akk");
            us.setLogin("log");
            appUserService.register(us);

            var users = appUserService.getAll();
            InformationOrm information1 = new InformationOrm(
                    "Title 1", "Content 1", "http://test.cc", LocalDate.now(), new CategoryOrm("food"), users.get(0)
            );
            repository.create(information1);

            InformationOrm information2 = new InformationOrm(
                    "Title 2", "Content 2", "http://test.cc", LocalDate.now(), new CategoryOrm("food"), users.get(0)
            );
            repository.create(information2);

            InformationOrm information3 = new InformationOrm(
                    "Title 3", "Content 3", "http://test.cc", LocalDate.now(), new CategoryOrm("cars"), users.get(0)
            );
            repository.create(information3);
        };
    }
}