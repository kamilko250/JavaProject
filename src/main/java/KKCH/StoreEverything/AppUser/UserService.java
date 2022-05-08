package KKCH.StoreEverything.AppUser;

import java.util.Optional;

public interface UserService {
    AppUser register(final AppUserDto user) throws Exception;
    void login(String login, String password) throws Exception;
    void logout();
    AppUserDto getCurrentUser();
    boolean checkIfUserExist(Long id);
    Optional<AppUser> getUserById(final Long id) throws Exception;
}
