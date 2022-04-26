package KKCH.StoreEverything.AppUser;

import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Role.UserRole;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String password;
    private Integer age;
    
    @OneToMany(
            mappedBy = "appUser",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<InformationOrm> informationOrms;

    @ManyToMany
    private Set<UserRole> roles;

    public AppUser() {
    }

    public AppUser(String name, String surname, String email, String password, Integer age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public AppUser(Long id, String name, String surname, String email, String password, Integer age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<InformationOrm> getInformationOrms() {
        return informationOrms.stream().toList();
    }

    public void setInformationOrms(List<InformationOrm> informationOrms) {
        this.informationOrms = informationOrms.stream().collect(Collectors.toSet());
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
