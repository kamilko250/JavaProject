package KKCH.StoreEverything.Information;

import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InformationDto {
   private Long id;
    @NotEmpty(message="Pole wymagane")
    @NotNull
    @Size(max = 20, min = 3 , message = "Tytuł musi mieć między 3 a 20 znaków")
    private String title;
    @NotEmpty(message="Pole wymagane")
    @NotNull
    @Size(max = 500, min = 5, message = "Treść musi mieć między 5 a 500 znaków")
    private String content;
    @URL(message = "Pole musi mieć format adresu strony internetowej")
    private String link;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate addDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reminderDate;

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