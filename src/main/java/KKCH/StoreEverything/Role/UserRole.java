package KKCH.StoreEverything.Role;

import KKCH.StoreEverything.AppUser.AppUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLE")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users;

    public UserRole(String name){
        this.name = name;
    }

    public UserRole() {

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

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    @Override
    public String toString(){
        return "ID: " + this.id + " Role " + this.name;
    }
}
