package com.wp.system.services.category;

import com.wp.system.entity.category.Category;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.category.CategoryErrorCode;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    public List<Category> getUserCategories(UUID userId) {
        if(userId == null)
            throw new ServiceException(CategoryErrorCode.NO_USER_ID);

        return categoryRepository.getAllUserCategories(userId);
    }

    public Category createCategory(CreateCategoryRequest request) {
        User user = this.userService.getUserById(request.getUserId());

        Category category = new Category(request.getName(), request.getColor(), request.getDescription(), user, request.getIcon());

        categoryRepository.save(category);

        return category;
    }

    public Category editCategory(EditCategoryRequest request, UUID categoryId) {
        Category category = this.getCategoryById(categoryId);

        if(request.getDescription() != null && !category.getDescription().equals(request.getDescription()))
            category.setDescription(request.getDescription());

        if(request.getName() != null && !category.getName().equals(request.getName()))
            category.setName(request.getName());

        if(request.getColor() != null && !category.getColor().equals(request.getColor()))
            category.setColor(request.getColor());

        if(request.getIcon() != null && !category.getCategoryIcon().equals(request.getIcon()))
            category.setCategoryIcon(request.getIcon());

        categoryRepository.save(category);

        return category;
    }

    @Transactional
    public Category removeCategory(UUID categoryId) {
        Category category = this.getCategoryById(categoryId);

        this.categoryRepository.delete(category);

        return category;
    }

    public Category getCategoryById(UUID id) {
        Optional<Category> foundCategory = this.categoryRepository.findById(id);

        if(foundCategory.isEmpty())
            throw new ServiceException(CategoryErrorCode.NOT_FOUND);

        return foundCategory.get();
    }
}
