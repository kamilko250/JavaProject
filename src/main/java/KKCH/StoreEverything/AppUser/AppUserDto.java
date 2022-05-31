package KKCH.StoreEverything.AppUser;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class AppUserDto {
    private Long id;

    @NotEmpty(message="Field required")
    @Size(max = 20, min = 2, message = "Name must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-ZĄŻŹĆÓŁĘ][a-zążźćłóę]*(\\s[A-ZĄŻŹĆÓŁĘ][a-zążźćłóę]*)*$")
    private String name;

    @NotEmpty(message="Surname required")
    @Size(max = 50, min = 3, message = "Surname must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-ZĄŻŹĆÓŁĘ][a-zążźćłóę]*(\\s[A-ZĄŻŹĆÓŁĘ][a-zążźćłóę]*)*$")
    private String surname;

    @NotEmpty(message="E-mail required")
    @Size(max = 35, min = 2, message = "Email must be between 3 and 35 characters")
    @Email
    private String email;

    @NotEmpty(message="Password required")
    @Size(max = 35, min = 5, message = "Password must be at least 5 characters")
    private String password;

    @Min(value = 18, message = "must be equal or greater than 18")
    private Integer age;

    @NotEmpty
    @Pattern(message = "Only small letter", regexp = "^([a-ząćęółżź\\d]){5,}$")
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