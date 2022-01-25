package com.wp.system.services.category;

import com.wp.system.entity.category.Category;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.other.SystemImageTag;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.request.category.CreateCategoryRequest;
import com.wp.system.request.category.EditCategoryRequest;
import com.wp.system.services.image.ImageService;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ImageService imageService;

    public List<Category> getUserCategories(UUID userId) {
        return categoryRepository.getAllUserCategories(userId);
    }

    public Category createCategory(CreateCategoryRequest request) {
        User user = this.userService.getUserById(request.getUserId());

        SystemImage image = null;

        if(request.getIcon() != null) {
            image = this.imageService.getImageById(request.getIcon());

            if(!image.getTag().equals(SystemImageTag.CATEGORY_ICON))
                throw new ServiceException("Invalid Image Tag", HttpStatus.BAD_REQUEST);
        }

        Category category = new Category(request.getName(), request.getColor(), request.getDescription(), user, image);

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

        if(request.getIcon() != null && !request.getIcon().equals(category.getIcon().getId())) {
            SystemImage image = this.imageService.getImageById(request.getIcon());

            if(!image.getTag().equals(SystemImageTag.CATEGORY_ICON))
                throw new ServiceException("Invalid Image tag", HttpStatus.BAD_REQUEST);

            category.setIcon(image);
        }

        if(request.getUserId() != null && !category.getUser().getId().equals(request.getUserId())) {
            User user = userService.getUserById(request.getUserId());

            category.setUser(user);
        }

        if(request.getCategoryLimit() != null && category.getCategoryLimit() != request.getCategoryLimit())
            category.setCategoryLimit(request.getCategoryLimit());

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
            throw new ServiceException("Category not found", HttpStatus.BAD_REQUEST);

        return foundCategory.get();
    }
}
