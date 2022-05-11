package KKCH.StoreEverything;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserService;
import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Information.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/sharing")
public class ShareController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private AppUserService appUserService;

    public ShareController(InformationService informationService, AppUserService appUserService) {
        this.informationService = informationService;
        this.appUserService = appUserService;
    }

    @PostMapping("/public")
    public String shareInformation(@RequestBody int informationId){
        //   should return full address for shared element exampole: http://localhost:8080/shared/1
        // share based on link TODO for id

        Optional<InformationOrm>  informationOrm = informationService.getById(Long.valueOf(informationId));
        if(informationOrm.isPresent()) {
            InformationOrm information = informationOrm.get();
            information.setPublic(true);
            return "http://localhost:8080/share/" + information.getId();
        }
        return ".|.";
    }

    @PostMapping("/users")
    public String shareForUsers (@RequestBody() List<Integer> users, @RequestParam int id) {
        //   should return full address for shared element exampole: http://localhost:8080/shared/1
        // share based on link TODO for id

        List<AppUser> allowedUsers = new ArrayList<>();

        for(var user : users)
        {
            AppUser appUser = appUserService.get(Long.valueOf(user));
            if(appUser != null)
                allowedUsers.add(appUser);
        }

        Optional<InformationOrm>  informationOrm = informationService.getById(Long.valueOf(id));
        if(informationOrm.isPresent() && allowedUsers.stream().count() > 0)
        {
            informationOrm.get().addAllowedUsers(allowedUsers);
            informationOrm.get().setPublic(false);
            return "http://localhost:8080/share/" + informationOrm.get().getId();

        }
        return "";
    }
}