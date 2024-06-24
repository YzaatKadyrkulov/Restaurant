package peaksoft.restaurant.service;


import peaksoft.restaurant.dto.request.category.SaveCategoryRtRd;
import peaksoft.restaurant.dto.request.subcategory.SubcategorySaveRqRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.subcategory.SubcategoryResponseRd;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {
    SimpleResponse addSubcategory(Long categoryId, Long menuId,SubcategorySaveRqRd subcategorySaveRqRd);

    SimpleResponse updateSubCategory(Long subcategoryId, SubcategorySaveRqRd subcategorySaveRqRd);

    SimpleResponse deleteSubCategory(Long subcategoryId);

    Optional<SubcategoryResponseRd> findSubCategoryById(Long subcategoryId);

    List<SubcategoryResponseRd> findAllSubcategories();

    List<SubcategoryResponseRd> findByCategoryIdOrderByName(Long categoryId);

    List<SubcategoryResponseRd> getAllSubCategoriesByCategoryIdGrouped(Long categoryId);
}
