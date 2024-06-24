package peaksoft.restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.request.category.SaveCategoryRtRd;
import peaksoft.restaurant.dto.response.category.CategoryRsRd;
import peaksoft.restaurant.exception.NotFoundException;
import peaksoft.restaurant.dto.response.restaurant.SimpleResponse;
import peaksoft.restaurant.entities.Category;
import peaksoft.restaurant.repository.CategoryRepository;
import peaksoft.restaurant.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse saveCategory(SaveCategoryRtRd saveCategoryRtRd) {
        Category category = new Category();
        category.setName(saveCategoryRtRd.name());

        categoryRepository.save(category);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with id: " + category.getId() + " is saved")
                .build();
    }

    @Override
    public SimpleResponse updateCategory(Long id, SaveCategoryRtRd saveCategoryRtRd) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category with id %d not found", id)));

        category.setName(saveCategoryRtRd.name());

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with id: " + id + " is update")
                .build();
    }

    @Override
    public SimpleResponse deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id: " + id + " is not found");
        }

        categoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with id " + id + " was deleted")
                .build();
    }

    @Override
    public Optional<CategoryRsRd> getCategoryById(Long id) {
        return Optional.ofNullable(categoryRepository.getCategoriesById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category with id %d not found", id))));
    }

    @Override
    public List<CategoryRsRd> findAllCategories() {
        return categoryRepository.findAllCategories();
    }
}
