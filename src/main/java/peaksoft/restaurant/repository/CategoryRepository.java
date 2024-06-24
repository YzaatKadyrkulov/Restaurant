package peaksoft.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.request.category.SaveCategoryRtRd;
import peaksoft.restaurant.dto.response.category.CategoryRsRd;
import peaksoft.restaurant.entities.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Optional<CategoryRsRd> getCategoriesById(Long id);

    @Query("select new peaksoft.restaurant.dto.response.category.CategoryRsRd(c.id,c.name) from Category c")
    List<CategoryRsRd> findAllCategories();
}
