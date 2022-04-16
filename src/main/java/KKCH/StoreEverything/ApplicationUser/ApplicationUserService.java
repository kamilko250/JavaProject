package KKCH.StoreEverything.User;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class UserService {
    public List<User> getUsers()
    {
        return List.of(
                new User(1L,
                        "Kamil",
                        "kamilko250@wp.pl",
                        22,
                        LocalDate.of(2000, Month.APRIL,21)
                )
        );
    }
}
