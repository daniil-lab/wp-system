package com.wp.system.controller.category;

import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.category.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Category API")
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "Bearer")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('CATEGORY_CREATE', 'CATEGORY_FULL')")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<CategoryDTO>> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new CategoryDTO(this.categoryService.createCategory(request)), "Category created"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_UPDATE', 'CATEGORY_FULL')")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<CategoryDTO>> updateCategory(@RequestBody EditCategoryRequest request, @PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new CategoryDTO(this.categoryService.editCategory(request, categoryId)), "Category updated"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_DELETE', 'CATEGORY_FULL')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<CategoryDTO>> removeCategory(@PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new CategoryDTO(this.categoryService.removeCategory(categoryId)), "Category removed"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_GET', 'CATEGORY_FULL')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<CategoryDTO>> getCategoryById(@PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new CategoryDTO(this.categoryService.getCategoryById(categoryId)), "Category returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_GET', 'CATEGORY_FULL')")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<CategoryDTO>>> getUserCategories(@RequestParam UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.categoryService.getUserCategories(userId).stream().map(CategoryDTO::new).collect(Collectors.toList()), "Categories returned"), HttpStatus.OK);
    }
}
