package KKCH.StoreEverything;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sharing")
public class ShareController {

    @PostMapping("/public")
    public String shareInformation(@RequestBody int informationId){
     //   should return full address for shared element exampole: http://localhost:8080/shared/1
        // share based on link TODO for id

        return "http://localhost:8080/share/1";
    }

    @PostMapping("/users")
    public String shareForUsers (@RequestBody() List<Integer> users, @RequestParam int id) {
        //   should return full address for shared element exampole: http://localhost:8080/shared/1
        // share based on link TODO for id

        return "http://localhost:8080/share/1";
    }
}