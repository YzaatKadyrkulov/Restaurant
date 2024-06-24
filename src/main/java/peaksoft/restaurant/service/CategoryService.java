package peaksoft.restaurant.service;

import peaksoft.restaurant.dto.request.category.SaveCategoryRtRd;
import peaksoft.restaurant.dto.response.category.CategoryRsRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    SimpleResponse saveCategory(SaveCategoryRtRd saveCategoryRtRd);

    SimpleResponse updateCategory(Long id, SaveCategoryRtRd saveCategoryRtRd);

    SimpleResponse deleteCategoryById(Long id);

    Optional<CategoryRsRd> getCategoryById(Long id);

    List<CategoryRsRd> findAllCategories();
}
