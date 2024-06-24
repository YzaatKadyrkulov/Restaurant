package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.request.category.SaveCategoryRtRd;
import peaksoft.restaurant.dto.request.subcategory.SubcategorySaveRqRd;
import peaksoft.restaurant.dto.response.category.CategoryRsRd;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.dto.response.subcategory.SubcategoryResponseRd;
import peaksoft.restaurant.service.SubCategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryApi {
    private final SubCategoryService subCategoryService;

    @PostMapping("/save/{categoryId}/{menuId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse addSubcategory(@PathVariable Long categoryId,
                                         @PathVariable Long menuId,
                                         @RequestBody SubcategorySaveRqRd subcategorySaveRqRd) {
        return subCategoryService.addSubcategory(categoryId, menuId, subcategorySaveRqRd);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse updateCategory(@PathVariable Long id,
                                         @RequestBody SubcategorySaveRqRd subcategorySaveRqRd) {
        return subCategoryService.updateSubCategory(id, subcategorySaveRqRd);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteSubCategoryById(@PathVariable Long id) {
        return subCategoryService.deleteSubCategory(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<SubcategoryResponseRd> getCategoryById(@PathVariable Long id) {
        return subCategoryService.findSubCategoryById(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubcategoryResponseRd> findAllCategories() {
        return subCategoryService.findAllSubcategories();
    }

    @GetMapping("/findByCategoryIdOrderByName/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubcategoryResponseRd> findByCategoryIdOrderByName(@PathVariable Long categoryId) {
        return subCategoryService.findByCategoryIdOrderByName(categoryId);
    }

    @GetMapping("/getAllSubCategoriesByCategoryIdGrouped/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubcategoryResponseRd> getAllSubCategoriesByCategoryIdGrouped(@PathVariable Long categoryId) {
        return subCategoryService.getAllSubCategoriesByCategoryIdGrouped(categoryId);
    }
}
