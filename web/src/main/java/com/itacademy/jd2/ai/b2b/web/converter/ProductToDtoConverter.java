package com.itacademy.jd2.ai.b2b.web.converter;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.web.dto.ProductDTO;

@Component
public class ProductToDtoConverter implements Function<IProduct, ProductDTO> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ProductToDtoConverter.class);

    @Override
    public ProductDTO apply(final IProduct entity) {
        final ProductDTO ProductDto = new ProductDTO();
        ProductDto.setId(entity.getId());
        ProductDto.setName(entity.getName());
        ProductDto.setDescription(entity.getDescription());
        ProductDto.setExternalLink(entity.getExternalLink());
        ProductDto.setVisible(entity.getVisible());
        ProductDto.setPosition(entity.getPosition());
        ProductDto.setPrice(entity.getPrice());
        ProductDto.setQuantityStock(entity.getQuantityStock());

        ICategory category = entity.getCategory();
        if (category != null) {
            ProductDto.setCategoryId(category.getId());
            ProductDto.setCategoryName(category.getName());
        }

        IBrand brand = entity.getBrand();
        if (brand != null) {
            ProductDto.setBrandId(brand.getId());
            ProductDto.setBrandName(brand.getName());
        }

        try {
            final Set<IImage> engines = entity.getImages();
            if (engines != null) {
                ProductDto.setImageIds(
                        engines.stream().map(IImage::getId).collect(Collectors.toSet()));
            }
        } catch (final LazyInitializationException e) {
            LOGGER.warn("ignore conversion of 'images' collection because of:"
                    + e.getMessage());
        }

        ProductDto.setCreated(entity.getCreated());
        ProductDto.setUpdated(entity.getUpdated());
        ProductDto.setVersion(entity.getVersion());
        return ProductDto;
    }

}
