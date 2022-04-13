package KKCH.StoreEverything.Information;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "information")
public class InformationOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "information_generator")
    @SequenceGenerator(name="information_generator", sequenceName = "information_sequence", allocationSize=50)
    private Long id;
    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    private String link;
    private LocalDate addDate;
    private LocalDateTime remimderDate;

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
}