package KKCH.StoreEverything.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    //@Query("SELECT t FROM users t WHERE t.email = ?1")
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByName(String name);
}
