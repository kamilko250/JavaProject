package KKCH.StoreEverything.AppUser;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class AppUserDto {
    private Long id;

    @NotEmpty(message="Field required")
    @NotNull
    @Size(max = 20, min = 2, message = "Name must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Z].*")
    private String name;

    @NotEmpty(message="Surname required")
    @NotNull
    @Size(max = 50, min = 3, message = "Surname must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Z].*")
    private String surname;

    @NotEmpty(message="E-mail required")
    @NotNull
    @Size(max = 35, min = 2, message = "Email must be between 3 and 35 characters")
    @Email
    private String email;

    @NotEmpty(message="Password required")
    @NotNull
    @Size(max = 35, min = 5, message = "Password must be at least 5 characters")
    //@Pattern(regexp = )
    private String password;

    @NotEmpty(message="Age required")
    @NotNull
    @Size(max = 3, min = 2, message = "User must be older or equal to 18")
    private Integer age;


    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[a-z]*$")
    private String login;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
