package KKCH.StoreEverything.AppUser;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AppUserDto {
    private Long id;

    @NotEmpty(message="Field required")
    @NotNull
    @Size(max = 20, min = 2, message = "Name must be between 3 and 20 characters")
    private String name;

    @NotEmpty(message="Surname required")
    @NotNull
    @Size(max = 50, min = 3, message = "Surname must be between 3 and 50 characters")
    private String surname;

    @NotEmpty(message="E-mail required")
    @NotNull
    @Size(max = 35, min = 2, message = "Email must be between 3 and 35 characters")
    private String email;

    @NotEmpty(message="E-mail required")
    @NotNull
    @Size(max = 35, min = 2, message = "Email must be between 3 and 35 characters")
    private String password;

    @NotEmpty(message="E-mail required")
    @NotNull
    @Size(max = 35, min = 2, message = "User must be older or equal to 18")
    private Integer age;

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
}