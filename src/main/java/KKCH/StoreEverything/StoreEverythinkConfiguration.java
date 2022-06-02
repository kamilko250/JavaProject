package KKCH.StoreEverything;

import KKCH.StoreEverything.AppUser.AppUserRepository;
import KKCH.StoreEverything.AppUser.AppUserService;
import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Information.InformationService;
import KKCH.StoreEverything.Role.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class StoreEverythinkConfiguration {
    //@Transactional
    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository appUserRepository, UserRoleRepository userRoleRepository, InformationService repository, AppUserService appUserService) {
        return args -> {
            //SetupDataLoader

            var users = appUserService.getAll();
            InformationOrm information1 = new InformationOrm(
                    "Title 1", "Content 1", LocalDate.parse("2022-01-01"), new CategoryOrm("food"), users.get(0), false, "https://google.com"
                    );
            repository.create(information1);

            InformationOrm information2 = new InformationOrm(
                    "Title 2", "Content 2", LocalDate.now(), new CategoryOrm("food"), users.get(0), false,"https://google.com"
                    );
            repository.create(information2);

            InformationOrm information3 = new InformationOrm(
                    "Title 3", "Content 3", LocalDate.now(), new CategoryOrm("cars"), users.get(1), false,"https://google.com"
                    );
            information3.addAllowedUser(users.get(0));
            repository.create(information3);
        };
    }
}