package KKCH.StoreEverything.AppUser;

import java.util.Optional;

public interface UserService {
    AppUser register(final AppUserDto user) throws Exception;
    void login(String username, String password) throws Exception;
    void logout();
    AppUser getCurrentUser();
    boolean checkIfUserExist(final AppUserDto userDto);
    Optional<AppUser> getUserById(final Long id) throws Exception;
}
