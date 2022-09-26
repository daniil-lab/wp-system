package com.wp.system.controller.category;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.category.BaseCategoryDTO;
import com.wp.system.request.category.CreateBaseCategoryRequest;
import com.wp.system.request.category.EditBaseCategoryRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.category.BaseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Base Category API")
@RequestMapping("/api/v1/base-category")
@SecurityRequirement(name = "Bearer")
public class BaseCategoryController extends DocumentedRestController {
    @Autowired
    private BaseCategoryService baseCategoryService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BASE_CATEGORY_CREATE', 'BASE_CATEGORY_FULL')")
    @Operation(summary = "Создание базовой категории")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<BaseCategoryDTO>> createBaseCategory(@Valid @RequestBody CreateBaseCategoryRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new BaseCategoryDTO(this.baseCategoryService.createBaseCategory(request)), "Category created"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BASE_CATEGORY_UPDATE', 'BASE_CATEGORY_FULL')")
    @Operation(summary = "Изменение базовой категории")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<BaseCategoryDTO>> updateBaseCategory(@Valid @RequestBody EditBaseCategoryRequest request, @PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BaseCategoryDTO(this.baseCategoryService.updateBaseCategory(request, categoryId)), "Category updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BASE_CATEGORY_DELETE', 'BASE_CATEGORY_FULL')")
    @Operation(summary = "Удаление базовой категории")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<BaseCategoryDTO>> removeBaseCategory(@PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BaseCategoryDTO(this.baseCategoryService.removeBaseCategory(categoryId)), "Category removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BASE_CATEGORY_GET', 'BASE_CATEGORY_FULL')")
    @Operation(summary = "Получение базовой категории по ID")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<BaseCategoryDTO>> getBaseCategoryById(@PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new BaseCategoryDTO(this.baseCategoryService.getBaseCategoryById(categoryId)), "Category returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BASE_CATEGORY_GET', 'BASE_CATEGORY_FULL')")
    @Operation(summary = "Получение всех базовых категорий")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<BaseCategoryDTO>>> getAllBaseCategories() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.baseCategoryService.getAllBaseCategories().stream().map(BaseCategoryDTO::new).collect(Collectors.toList()), "Category returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('BASE_CATEGORY_GET', 'BASE_CATEGORY_FULL')")
    @Operation(summary = "Получение всех базовых категорий постранично")
    @GetMapping("/paged")
    public ResponseEntity<ServiceResponse<List<BaseCategoryDTO>>> getBaseCategoriesPaged(
            @RequestParam
                int page,
            @RequestParam
                int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.baseCategoryService.getBaseCategoriesPaged(page, pageSize).stream().map(BaseCategoryDTO::new).collect(Collectors.toList()), "Category returned"), HttpStatus.OK);
    }
}
