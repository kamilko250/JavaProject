package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.Category.CategoryOrm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InformationOrmTest {

    @Test
    public void AddAllowedUserTest(){
        AppUser appUser = new AppUser();
        InformationOrm informationOrm = new InformationOrm();
        informationOrm.addAllowedUser(appUser);

        assert informationOrm.getAllowedUsers().stream().findFirst().get() == appUser;
    }

    @Test
    public void AddCategoryTest(){
        CategoryOrm categoryOrm = new CategoryOrm();
        InformationOrm informationOrm = new InformationOrm();
        informationOrm.setCategory(categoryOrm);

        assert informationOrm.getCategory() == categoryOrm;
    }

    @Test
    public void
}
