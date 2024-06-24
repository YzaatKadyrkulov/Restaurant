package peaksoft.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.response.subcategory.SubcategoryResponseRd;
import peaksoft.restaurant.entities.SubCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    Optional<SubCategory> findByName(String name);

    Optional<SubcategoryResponseRd> findSubCategoriesById(Long subcategoryId);

    @Query("select new peaksoft.restaurant.dto.response.subcategory.SubcategoryResponseRd(s.id,s.name) from SubCategory s")
    List<SubcategoryResponseRd> findAllSubcategories();

    @Query("select new peaksoft.restaurant.dto.response.subcategory.SubcategoryResponseRd(s.id, s.name) " +
            "from SubCategory s where s.category.id = :categoryId order by s.name")
    List<SubcategoryResponseRd> findByCategoryIdOrderByName(Long categoryId);

    @Query("select new peaksoft.restaurant.dto.response.subcategory.SubcategoryResponseRd(s.id, s.name)" +
            "from SubCategory s where s.category.id = :categoryId group by s.category.id, s.id, s.name")
    List<SubcategoryResponseRd> getAllSubCategoriesByCategoryIdGrouped(Long categoryId);
}