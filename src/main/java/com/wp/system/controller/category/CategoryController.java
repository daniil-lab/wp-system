package com.wp.system.controller.category;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.other.CategoryColor;
import com.wp.system.other.CategoryIcon;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.response.category.CategoryColorResponse;
import com.wp.system.response.category.CategoryIconResponse;
import com.wp.system.services.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Category API")
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "Bearer")
public class CategoryController extends DocumentedRestController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('CATEGORY_CREATE', 'CATEGORY_FULL')")
    @Operation(summary = "Создание категории и прикрепление ее к пользователю")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<CategoryDTO>> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new CategoryDTO(this.categoryService.createCategory(request)), "Category created"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_UPDATE', 'CATEGORY_FULL')")
    @Operation(summary = "Изменение категории")
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<CategoryDTO>> updateCategory(@RequestBody EditCategoryRequest request, @PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new CategoryDTO(this.categoryService.editCategory(request, categoryId)), "Category updated"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_DELETE', 'CATEGORY_FULL')")
    @Operation(summary = "Удаление категории")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<CategoryDTO>> removeCategory(@PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new CategoryDTO(this.categoryService.removeCategory(categoryId)), "Category removed"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_GET', 'CATEGORY_FULL')")
    @Operation(summary = "Получение категории по ID")
    @GetMapping("/{categoryId}")
    public ResponseEntity<ServiceResponse<CategoryDTO>> getCategoryById(@PathVariable UUID categoryId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new CategoryDTO(this.categoryService.getCategoryById(categoryId)), "Category returned"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORY_GET', 'CATEGORY_FULL')")
    @Operation(summary = "Получение всех категорий пользователя")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<CategoryDTO>>> getUserCategories(@RequestParam UUID userId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.categoryService.getUserCategories(userId).stream().map(CategoryDTO::new).collect(Collectors.toList()), "Categories returned"), HttpStatus.OK);
    }

    @Operation(summary = "Получение цветов категорий")
    @GetMapping("/colors")
    public ResponseEntity<ServiceResponse<List<CategoryColorResponse>>> getCategoryColors() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), Arrays.stream(CategoryColor.values()).map(CategoryColorResponse::new).collect(Collectors.toList()), "Category Colors returned"), HttpStatus.OK);
    }

    @Operation(summary = "Получение иконок категорий")
    @GetMapping("/icons")
    public ResponseEntity<ServiceResponse<List<CategoryIconResponse>>> getCategoryIcons() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), Arrays.stream(CategoryIcon.values()).map(icon -> new CategoryIconResponse(icon.getIconName(), icon.name(), "/icon/" + icon.name(), "/icon/dark/" + icon.name())).collect(Collectors.toList()), "Category Icons returned"), HttpStatus.OK);
    }

    @Operation(summary = "Получение изображения иконки категории (светлая тема)")
    @GetMapping("/icon/{iconSystemName}")
    public ResponseEntity<byte[]> getCategoryIcon(@PathVariable CategoryIcon iconSystemName) throws IOException {
        File image = new File(iconSystemName.getIconPath());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(iconSystemName.getMediaType());

        byte[] bytes = Files.readAllBytes(image.toPath());

        return ResponseEntity.ok().headers(httpHeaders).body(bytes);
    }

    @Operation(summary = "Получение изображения иконки категории (темная тема)")
    @GetMapping("/icon/dark/{iconSystemName}")
    public ResponseEntity<byte[]> getDarkCategoryIcon(@PathVariable CategoryIcon iconSystemName) throws IOException {
        File image = new File(iconSystemName.getDarkIconPath());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(iconSystemName.getMediaType());

        byte[] bytes = Files.readAllBytes(image.toPath());

        return ResponseEntity.ok().headers(httpHeaders).body(bytes);
    }
}
