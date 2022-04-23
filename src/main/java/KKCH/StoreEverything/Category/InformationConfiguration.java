package KKCH.StoreEverything.Category;

import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Information.InformationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class InformationConfiguration {
    @Bean
    CommandLineRunner commandLineRunnerInformation(InformationService repository) {
        return args -> {
            InformationOrm information1 = new InformationOrm(
                "Title 1", "Content 1", "http://test.cc", LocalDate.now(), new CategoryOrm ("food")
            );
            repository.create(information1);

            InformationOrm information2 = new InformationOrm(
                    "Title 2", "Content 2", "http://test.cc", LocalDate.now(), new CategoryOrm("food")
            );
            repository.create(information2);

            InformationOrm information3 = new InformationOrm(
                    "Title 3", "Content 3", "http://test.cc", LocalDate.now(), new CategoryOrm("cars")
            );
            repository.create(information3);
        };
    }
}