package com.itacademy.jd2.ai.b2b.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;
import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;
import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;
import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;
import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;
import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;
import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public abstract class AbstractTest {

    @Autowired
    private IAttributeService attributeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderObjectService orderService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IProductService productService;

    private static final Random RANDOM = new Random();
    private final double newDouble = new BigDecimal(getRandomDouble())
            .setScale(2, RoundingMode.UP).doubleValue(); // округляем Double до
                                                         // 2х знаков

    public double getNewDouble() {
        return newDouble;
    }

    public double getRandomDouble() {
        return RANDOM.nextDouble();
    }

    protected String getRandomPrefix() {
        return RANDOM.nextInt(99999) + "";
    }

    protected int getRandomObjectsCount() {
        return RANDOM.nextInt(9) + 1;
    }

    protected boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }

    public Random getRANDOM() {
        return RANDOM;
    }
    /*
     * final RoomType[] roomTypes = RoomType.values(); int randomIndex =
     * Math.max(0, RANDOM.nextInt(roomTypes.length) - 1);
     * entity.setType(roomTypes[randomIndex]);
     */

    public static final List<UserRole> VALUES = Collections
            .unmodifiableList(Arrays.asList(UserRole.values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM1 = new Random();

    protected static UserRole getRandomUserRole() {
        return VALUES.get(RANDOM1.nextInt(SIZE));
    }

    public IAttributeService getAttributeService() {
        return attributeService;
    }

    public IUserService getUserService() {
        return userService;
    }

    public IOrderObjectService getOrderService() {
        return orderService;
    }

    public IImageService getImageService() {
        return imageService;
    }

    public IBrandService getBrandService() {
        return brandService;
    }

    public ICategoryService getCategoryService() {
        return categoryService;
    }

    public IOrderItemService getOrderItemService() {
        return orderItemService;
    }

    public IProductService getProductService() {
        return productService;
    }

    protected IImage saveNewImage() {
        final IImage entity = getImageService().createEntity();
        entity.setName("image from brand-" + getRandomPrefix());
        entity.setUrl("test image url-" + getRandomPrefix());
        entity.setPosition(getRandomObjectsCount());
        getImageService().save(entity);
        return entity;
    }

    protected ICategory saveNewCategory() {
        final ICategory entity = getCategoryService().createEntity();
        entity.setName("user-" + getRandomPrefix());
        entity.setDescription("Description-" + getRandomPrefix());
        entity.setPosition(getRandomObjectsCount());
        entity.setImage(saveNewImage());
        getCategoryService().save(entity);
        return entity;
    }

    protected IOrderItem saveNewOrderItem() {
        final IOrderItem entity = getOrderItemService().createEntity();
        entity.setQuantity(getRandomObjectsCount());
        entity.setProduct(saveNewProduct());
        entity.setOrderObject(saveNewOrder());
        getOrderItemService().save(entity);
        return entity;
    }

    protected IProduct saveNewProduct() {
        final IProduct entity = getProductService().createEntity();
        entity.setName(getRandomPrefix());
        entity.setDescription(getRandomPrefix());
        entity.setExternalLink(getRandomPrefix());
        entity.setVisible(getRandomBoolean());
        entity.setPosition(getRandomObjectsCount());
        entity.setPrice(getRandomDouble());
        entity.setQuantityStock(getRandomObjectsCount());
        entity.setCategory(saveNewCategory());
        entity.setBrand(saveNewBrand());
        getProductService().save(entity);
        return entity;
    }

    protected IOrderObject saveNewOrder() {

        final IOrderObject entity = getOrderService().createEntity();
        entity.setClose(getRandomBoolean());
        entity.setCreator(saveNewUser());
        getOrderService().save(entity);
        return entity;
    }

    protected IUser saveNewUser() {
        final IUser entity = getUserService().createEntity();
        entity.setName("user from order-" + getRandomPrefix());
        entity.setEmail("test User email-" + getRandomPrefix());
        entity.setPassword("test User password-" + getRandomPrefix());
        entity.setEnabled(getRandomBoolean());
        entity.setRole(getRandomUserRole());
        getUserService().save(entity);
        return entity;
    }

    protected IBrand saveNewBrand() {
        final IBrand entity = getBrandService().createEntity();
        entity.setName("name-" + getRandomPrefix());
        entity.setDescription("Description-" + getRandomPrefix());
        entity.setImage(saveNewImage());
        getBrandService().save(entity);
        return entity;
    }

    protected IAttribute saveNewAttribute() {
        final IAttribute entity = getAttributeService().createEntity();
        entity.setName("name-" + getRandomPrefix());
        entity.setValue("value-" + getRandomPrefix());
        getAttributeService().save(entity);
        return entity;
    }

}
