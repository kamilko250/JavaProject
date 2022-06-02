package KKCH.StoreEverything.AppUser;

import KKCH.StoreEverything.Information.InformationOrm;
import KKCH.StoreEverything.Role.UserRole;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "APPUSER")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String password;
    private Integer age;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_information",
            joinColumns = @JoinColumn(name = "appUser_id"),
            inverseJoinColumns = @JoinColumn(name = "information_id"))
    private List<InformationOrm> ownedInformations;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "appUser_id"),
            inverseJoinColumns = @JoinColumn(name = "userRole_id"))
    private Set<UserRole> roles;

    public AppUser() {
    }

    public AppUser(String name, String surname, String email, String password, Integer age, String login) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.login = login;
    }

    public AppUser(Long id, String name, String surname, String email, String password, Integer age, String login) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.age = age;
        this.login = login;
    }

    public AppUser(String name, String surname, String email, String password, Integer age, String login, Set<UserRole> roles) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.age = age;
        this.login = login;
        this.roles = roles;
    }

    public void addInformation(InformationOrm informationOrm){
        this.ownedInformations.add(informationOrm);
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

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public List<String> getRolesList(){
        return roles.stream().map(UserRole::getName).toList();
    }


}