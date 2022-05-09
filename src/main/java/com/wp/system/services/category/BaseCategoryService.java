package com.wp.system.services.category;

import com.wp.system.entity.category.BaseCategory;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.category.BaseCategoryRepository;
import com.wp.system.request.category.CreateBaseCategoryRequest;
import com.wp.system.request.category.EditBaseCategoryRequest;
import com.wp.system.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class BaseCategoryService {
    @Autowired
    private BaseCategoryRepository baseCategoryRepository;

    @Autowired
    private ImageService imageService;

    public List<BaseCategory> getAllBaseCategories() {
        return baseCategoryRepository.findAll();
    }

    public Page<BaseCategory> getBaseCategoriesPaged(int page, int pageSize) {
        return baseCategoryRepository.findAll(PageRequest.of(page, pageSize));
    }

    public BaseCategory getBaseCategoryById(UUID id) {
        return baseCategoryRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Base category not found", HttpStatus.NOT_FOUND);
        });
    }

    @Transactional
    public BaseCategory removeBaseCategory(UUID id) {
        BaseCategory category = getBaseCategoryById(id);

        baseCategoryRepository.delete(category);

        return category;
    }

    @Transactional
    public BaseCategory createBaseCategory(CreateBaseCategoryRequest request) {
        BaseCategory category = new BaseCategory(
                request.getName(),
                request.getColor(),
                request.getDescription()
        );

        if(request.getIcon() != null) {
            SystemImage image = imageService.getImageById(request.getIcon());

            category.setIcon(image);
        }

        category.setForEarn(request.getForEarn());
        category.setForSpend(request.getForSpend());

        baseCategoryRepository.save(category);

        return category;
    }

    @Transactional
    public BaseCategory updateBaseCategory(EditBaseCategoryRequest request, UUID id) {
        BaseCategory category = getBaseCategoryById(id);

        if(request.getName() != null)
            category.setName(request.getName());

        if(request.getDescription() != null)
            category.setDescription(request.getDescription());

        if(request.getColor() != null)
            category.setColor(request.getColor());

        if(request.getIcon() != null) {
            SystemImage image = imageService.getImageById(request.getIcon());

            category.setIcon(image);
        }

        if(request.getForEarn() != null)
            category.setForEarn(request.getForEarn());

        if(request.getForSpend() != null)
            category.setForSpend(request.getForSpend());

        return category;
    }
}
