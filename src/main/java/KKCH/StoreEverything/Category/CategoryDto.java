package KKCH.StoreEverything.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryDto {
    private Long id;
    @NotBlank(message = "Category name cannot be blank")
    @Size(min=3,max=20,message = "Category name length should be between 3 and 20 characters")
    @Pattern(message = "Only small letter" , regexp = "^[a-ząćęółżź]+(\\s[a-ząćęółżź]+)*$")
    private String name;

    public void setId (Long id) {
        this.id = id;
    }

    public Long getId () {
        return id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

}