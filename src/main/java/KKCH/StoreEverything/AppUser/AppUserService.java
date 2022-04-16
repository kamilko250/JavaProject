package KKCH.StoreEverything.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository)
    {
        this.appUserRepository = appUserRepository;
    }

    public AppUser get(Long id){
        Optional<AppUser> appUser = appUserRepository.findById(id);
        if(appUser.isPresent())
            return appUser.get();
        return null;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser update(AppUserDto userDto){
        Optional<AppUser> appUser = appUserRepository.findById(userDto.getId());
        if (appUser.isPresent()) {
            return appUserRepository.save(appUser.get());
        }
        AppUser newAppUser = new AppUser(
            userDto.getId(),
            userDto.getName(),
            userDto.getSurname(),
            userDto.getEmail(),
            userDto.getPassword(),
            userDto.getAge()
            );
        return appUserRepository.save(newAppUser);
    }

    public AppUser delete(AppUserDto userDto)
    {
        Optional<AppUser> appUser = appUserRepository.findById(userDto.getId());
        if (appUser.isPresent()) {
            appUserRepository.delete(appUser.get());
            return appUser.get();
        }
        return null;
    }
}
