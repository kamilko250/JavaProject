package KKCH.StoreEverything.AppUser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppUserConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository appUserRepository) {
        return args -> {
            AppUser kamil = new AppUser(
                    "Kamil",
                    "Kamil",
                    "Kamil",
                    "Kamil",
                    22
            );
            appUserRepository.save(kamil);
        };
    }
}
