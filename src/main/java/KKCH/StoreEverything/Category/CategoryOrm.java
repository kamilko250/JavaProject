package KKCH.StoreEverything.Category;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryOrm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_sequence", allocationSize = 50)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;

    public CategoryOrm () {
    }

    public CategoryOrm (String name) {
        this.name = name;
    }


    public void setId (Long id) {
        this.id = id;
    }

    public Long getId () {
        return id;
    }

    public void setName (String name) {
        this.name = name.toLowerCase();
    }

    public String getName () {
        return name;
    }

}