package KKCH.StoreEverything.Information;

import KKCH.StoreEverything.AppUser.AppUser;
import KKCH.StoreEverything.AppUser.AppUserDto;
import KKCH.StoreEverything.Category.CategoryDto;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InformationDto {
   private Long id;
    @NotEmpty(message="Field required")
    @NotNull
    @Size(max = 20, min = 3 , message = "Title length should be between 3 and 20 characters")
    private String title;
    @NotEmpty(message="Field required")
    @NotNull
    @Size(max = 500, min = 5, message = "Content length should be between 5 and 500 characters")
    private String content;
    @URL(message = "Link mus have URL format")
    private String link;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate addDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reminderDate;
    @NotNull(message = "Category cannot be empty")
    @Valid
    private CategoryDto category;
    private AppUserDto appUser;

    private boolean isPublic;

    public InformationDto(){}

    public InformationDto(CategoryDto category){
        this.category = category;
    }

    public CategoryDto getCategory () {
        return category;
    }

    public void setCategory (CategoryDto category) {
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

    public LocalDateTime getReminderDate () {
        return reminderDate;
    }

    public void setReminderDate (LocalDateTime reminderDate) {
        this.reminderDate = reminderDate;
    }

    public AppUserDto getAppUser () {
        return appUser;
    }

    public void setAppUser (AppUserDto appUser) {
        this.appUser = appUser;
    }


    public boolean isPublic () {
        return isPublic;
    }

    public void setPublic (boolean aPublic) {
        isPublic = aPublic;
    }

    private List<AppUser> allowedUsers = new ArrayList<>();

    public void addAllowedUsers (List<AppUser> appUser) {
        allowedUsers.addAll(appUser);
    }

    public void addAllowedUser (AppUser appUser) {
        allowedUsers.add(appUser);
    }

    public List<AppUser> getAllowedUsers () {
        return allowedUsers;
    }

    public void setAllowedUsers (List<AppUser> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    public boolean isUserAllowed (AppUser appUser) {
        return allowedUsers.contains(appUser) || isPublic;
    }


    @Override
    public String toString () {
        return "InformationDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", link='" + link + '\'' +
                ", addDate=" + addDate +
                ", reminderDate=" + reminderDate +
                '}';
    }
}