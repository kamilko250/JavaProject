package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationRepository extends JpaRepository<InformationOrm,Long> {

    public List<InformationOrm> findAllByAllowedUsers(AppUser user);
}