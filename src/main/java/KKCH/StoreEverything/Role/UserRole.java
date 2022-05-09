package KKCH.StoreEverything.Role;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.Role.Privilege.Privilege;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ROLE")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String title;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges;

    public UserRole (String name, String title) {
        this.name = name;
        this.title = title;
    }

    public UserRole () {

    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Set<AppUser> getUsers () {
        return users;
    }

    public void setUsers (Set<AppUser> users) {
        this.users = users;
    }

    public Set<Privilege> getPrivileges () {
        return privileges;
    }

    public void setPrivileges (Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    @Override
    public String toString () {
        return "ID: " + this.id + " Role " + this.name;
    }

    @Override public boolean equals (Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserRole userRole = (UserRole) o;
        return  Objects.equals(name, userRole.name);
    }

    @Override public int hashCode () {
        return 0;
    }
}