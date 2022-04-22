package com.wp.system.services.category;

import com.wp.system.entity.category.Category;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.SystemImageTag;
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

    @Autowired
    private AuthHelper authHelper;

    public List<Category> getUserCategories() {
        User user = authHelper.getUserFromAuthCredentials();

        return categoryRepository.getAllUserCategories(user.getId());
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

        category.setOnlyForEarn(request.getOnlyForEarn());
        category.setCategoryLimit(request.getCategoryLimit());

        categoryRepository.save(category);

        return category;
    }

    public Category editCategory(EditCategoryRequest request, UUID categoryId) {
        Category category = this.getCategoryById(categoryId);

        User user = authHelper.getUserFromAuthCredentials();

        if(!category.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

        if(request.getDescription() != null && !category.getDescription().equals(request.getDescription()))
            category.setDescription(request.getDescription());

        if(request.getName() != null && !category.getName().equals(request.getName()))
            category.setName(request.getName());

        if(request.getColor() != null && !category.getColor().equals(request.getColor()))
            category.setColor(request.getColor());

        if(request.getOnlyForEarn() != null) {
            category.setOnlyForEarn(request.getOnlyForEarn());
        }

        if(request.getIcon() != null) {
            SystemImage image = this.imageService.getImageById(request.getIcon());

            if(!image.getTag().equals(SystemImageTag.CATEGORY_ICON))
                throw new ServiceException("Invalid Image tag", HttpStatus.BAD_REQUEST);

            category.setIcon(image);
        }

        if(request.getCategoryLimit() != null && category.getCategoryLimit() != request.getCategoryLimit())
            category.setCategoryLimit(request.getCategoryLimit());

        categoryRepository.save(category);

        return category;
    }

    @Transactional
    public Category removeCategory(UUID categoryId) {
        Category category = this.getCategoryById(categoryId);

        User user = authHelper.getUserFromAuthCredentials();

        if(!category.getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

        category.setUser(null);
        category.setIcon(null);
        this.categoryRepository.delete(category);

        return category;
    }

    public Category getCategoryById(UUID id) {
        Optional<Category> foundCategory = this.categoryRepository.findById(id);
        User user = authHelper.getUserFromAuthCredentials();

        if(foundCategory.isEmpty())
            throw new ServiceException("Category not found", HttpStatus.BAD_REQUEST);

        if(!foundCategory.get().getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

        return foundCategory.get();
    }
}
