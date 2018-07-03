package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.service.IBrandService;
import com.itacademy.jd2.ai.b2b.service.ICategoryService;
import com.itacademy.jd2.ai.b2b.service.IImageService;
import com.itacademy.jd2.ai.b2b.service.IProductService;
import com.itacademy.jd2.ai.b2b.web.dto.ProductDTO;

@Component
public class ProductFromDtoConverter implements Function<ProductDTO, IProduct> {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IImageService imageService;

    @Override
    public IProduct apply(final ProductDTO dto) {
        final IProduct entity = productService.createEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setExternalLink(dto.getExternalLink());
        entity.setVisible(dto.getVisible());
        entity.setPosition(dto.getPosition());
        entity.setPrice(dto.getPrice());
        entity.setQuantityStock(dto.getQuantityStock());
        entity.setVersion(dto.getVersion());

        final ICategory category = categoryService.createEntity();
        category.setId(dto.getCategoryId());
        entity.setCategory(category);

        final IBrand brand = brandService.createEntity();
        brand.setId(dto.getBrandId());
        entity.setBrand(brand);

        final Set<Integer> imageIds = dto.getImageIds();
        if (CollectionUtils.isNotEmpty(imageIds)) {
            entity.setImages(imageIds.stream().map((id) -> {
                final IImage image = imageService.createEntity();
                image.setId(id);
                return image;
            }).collect(Collectors.toSet()));
        }

        return entity;
    }
}
