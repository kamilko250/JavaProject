package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.Category.CategoryOrm;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String link;

    //TODO (temporary) add non null to column definition and must be assigned by default to logged user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

    private LocalDate addDate;
    private LocalDateTime remimderDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryOrm category;


    public InformationOrm (String title, String content, String link, LocalDate addDate, CategoryOrm category) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.addDate = addDate;
        this.category = category;
    }

    public InformationOrm() {
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

    public String getLink () {
        return link;
    }

    public void setLink (String link) {
        this.link = link;
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
}