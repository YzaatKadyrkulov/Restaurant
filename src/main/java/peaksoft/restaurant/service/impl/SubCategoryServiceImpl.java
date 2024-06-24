package peaksoft.restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.request.subcategory.SubcategorySaveRqRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.subcategory.SubcategoryResponseRd;
import peaksoft.restaurant.entities.Category;
import peaksoft.restaurant.entities.Menuitem;
import peaksoft.restaurant.entities.SubCategory;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.repository.CategoryRepository;
import peaksoft.restaurant.repository.MenuItemRepository;
import peaksoft.restaurant.repository.SubCategoryRepository;
import peaksoft.restaurant.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public SimpleResponse addSubcategory(Long categoryId, Long menuId, SubcategorySaveRqRd subcategorySaveRqRd) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(String.format("Category with id %d not found", categoryId)));

        Menuitem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu item with id: " + menuId + " not found"));


        SubCategory subCategory = new SubCategory();
        subCategory.setName(subcategorySaveRqRd.name());
        category.getSubcategories().add(subCategory);
        subCategory.setCategory(category);
        category.getSubcategories().add(subCategory);
        menuItem.getSubcategories().add(subCategory);
        subCategory.setMenuitem(menuItem);

        subCategoryRepository.save(subCategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory is saved")
                .build();
    }

    @Override
    public SimpleResponse updateSubCategory(Long subcategoryId, SubcategorySaveRqRd subcategorySaveRqRd) {
        SubCategory subCategory = subCategoryRepository.findById(subcategoryId).orElseThrow(
                () -> new NotFoundException(String.format("SubCategory with id %d not found", subcategoryId)));

        subCategory.setName(subcategorySaveRqRd.name());

        subCategoryRepository.save(subCategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory with id: " + subCategory.getId() + " is updated")
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategory(Long subcategoryId) {
        if (!subCategoryRepository.existsById(subcategoryId)) {
            throw new NotFoundException("SubCategory with id: " + subcategoryId + " is not found");
        }

        subCategoryRepository.deleteById(subcategoryId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory with id " + subcategoryId + " was deleted")
                .build();
    }

    @Override
    public Optional<SubcategoryResponseRd> findSubCategoryById(Long subcategoryId) {
        return subCategoryRepository.findSubCategoriesById(subcategoryId);
    }

    @Override
    public List<SubcategoryResponseRd> findAllSubcategories() {
        return subCategoryRepository.findAllSubcategories();
    }

    @Override
    public List<SubcategoryResponseRd> findByCategoryIdOrderByName(Long categoryId) {
        return subCategoryRepository.findByCategoryIdOrderByName(categoryId);
    }

    @Override
    public List<SubcategoryResponseRd> getAllSubCategoriesByCategoryIdGrouped(Long categoryId) {
        return subCategoryRepository.getAllSubCategoriesByCategoryIdGrouped(categoryId);
    }
}
