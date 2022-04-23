package KKCH.StoreEverything.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryOrm,Long> {
    @Query("Select e from #{#entityName} e where name = :name")
    CategoryOrm findOneByName(@Param("name") String name);
}