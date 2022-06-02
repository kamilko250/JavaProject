package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.Category.CategoryOrm;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "information")
public class InformationOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "information_generator")
    @SequenceGenerator(name="information_generator", sequenceName = "information_sequence", allocationSize=50)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;

    //TODO (temporary) add non null to column definition and must be assigned by default to logged user
    @ManyToOne()
    private AppUser appUser;

    @ManyToMany
    private List<AppUser> allowedUsers = new ArrayList<>();

    private boolean isPublic;

    private String link;

    private LocalDate addDate;
    private LocalDateTime remimderDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryOrm category;


    public InformationOrm(String title, String content, LocalDate addDate, CategoryOrm category, AppUser user, boolean isPublic, String link) {
        this.title = title;
        this.content = content;
        this.addDate = addDate;
        this.category = category;
        this.appUser = user;
        this.isPublic = isPublic;
        this.link = link;
//        var allow = new ArrayList<AppUser>();
//        allow.add(user);
//        addAllowedUsers(allow);
    }

    public InformationOrm() {
    }

    public void addAllowedUsers(List<AppUser> appUser)
    {
        allowedUsers.addAll(appUser);
    }

    public void addAllowedUser (AppUser appUser) {
        allowedUsers.add(appUser);
    }

    public List<AppUser> getAllowedUsers () {
        return allowedUsers;
    }

    public boolean isUserAllowed(AppUser appUser)
    {
        return allowedUsers.contains(appUser) || isPublic;
    }

    public CategoryOrm getCategory () {
        return category;
    }

    public void setCategory (CategoryOrm category) {
        this.category = category;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }


    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }


    public LocalDate getAddDate () {
        return addDate;
    }

    public void setAddDate (LocalDate addDate) {
        this.addDate = addDate;
    }

    public LocalDateTime getRemimderDate () {
        return remimderDate;
    }

    public void setRemimderDate (LocalDateTime remimderDate) {
        this.remimderDate = remimderDate;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}