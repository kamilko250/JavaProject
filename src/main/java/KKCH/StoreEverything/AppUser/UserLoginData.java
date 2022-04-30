package KKCH.StoreEverything.AppUser;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserLoginData {
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[a-z]*$")
    private String login;

    @NotEmpty(message="Password required")
    @NotNull
    @Size(max = 35, min = 5, message = "Password must be at least 5 characters")
    //@Pattern(regexp = )
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
