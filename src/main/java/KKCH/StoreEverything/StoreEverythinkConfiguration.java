package KKCH.StoreEverything;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserRepository;
import KKCH.StoreEverything.AppUser.AppUserService;
import KKCH.StoreEverything.Category.CategoryOrm;
import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Information.InformationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class StoreEverythinkConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository appUserRepository, InformationService repository, AppUserService appUserService) {
        return args -> {
            AppUser kamil = new AppUser(
                    "Kamil",
                    "Kamil",
                    "Kamil",
                    "Kamil",
                    22
            );
            appUserRepository.save(kamil);
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