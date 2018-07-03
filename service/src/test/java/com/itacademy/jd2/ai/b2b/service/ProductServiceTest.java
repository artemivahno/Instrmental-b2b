package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.IProduct;

public class ProductServiceTest extends AbstractTest {

    @Before
    public void cleanTables() throws InterruptedException {
        getProductService().deleteAll();
        getOrderItemService().deleteAll();
        getCategoryService().deleteAll();
        getBrandService().deleteAll();
        getImageService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IProduct entity = saveNewProduct();

        final IProduct entityFromDb = getProductService().getFullInfo(entity.getId());
        // entity.setCategory(saveNewCategory());
        // entity.setBrand(saveNewBrand());

        assertEquals(entity.getName(), entityFromDb.getName());
        assertEquals(entity.getBrand().getId(), entityFromDb.getBrand().getId());
        assertNotNull(entityFromDb.getId());
        assertNotNull(entityFromDb.getDescription());
        assertNotNull(entityFromDb.getExternalLink());
        assertNotNull(entityFromDb.getVisible());
        assertNotNull(entityFromDb.getPosition());
        assertNotNull(entityFromDb.getPrice());
        assertNotNull(entityFromDb.getQuantityStock());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertNotNull(entityFromDb.getVersion());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        final IProduct entity = saveNewProduct();
        final IProduct entityFromDB = getProductService().get(entity.getId());
        final String newName = "new-name-" + getRandomPrefix();
        final String newDescription = "new-Description-" + getRandomPrefix();
        final String newExternalLink = "new-ExternalLink-" + getRandomPrefix();
        final Boolean newVisible = getRandomBoolean();
        final Integer newPosition = getRandomObjectsCount();
        final Double newPrice = getNewDouble();
        final Integer newQuantityStock = getRandomObjectsCount();
        final Integer newVersion = getRandomObjectsCount();
        entityFromDB.setName(newName);
        entityFromDB.setDescription(newDescription);
        entityFromDB.setExternalLink(newExternalLink);
        entityFromDB.setVisible(newVisible);
        entityFromDB.setPosition(newPosition);
        entityFromDB.setPrice(newPrice);
        entityFromDB.setQuantityStock(newQuantityStock);
        getProductService().save(entityFromDB);
        Thread.sleep(2);

        final IProduct udpatedEntityFromDB = getProductService()
                .get(entityFromDB.getId());
        assertEquals(newName, udpatedEntityFromDB.getName());
        assertEquals(newDescription, udpatedEntityFromDB.getDescription());
        assertEquals(newExternalLink, udpatedEntityFromDB.getExternalLink());
        assertEquals(newVisible, udpatedEntityFromDB.getVisible());
        assertEquals(newPosition, udpatedEntityFromDB.getPosition());
        assertEquals(newPrice, udpatedEntityFromDB.getPrice());

        assertEquals(newQuantityStock, udpatedEntityFromDB.getQuantityStock());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getProductService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewProduct();
        }

        final List<IProduct> allEntities = getProductService().getAll();

        for (final IProduct entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getName());
            assertNotNull(entityFromDb.getDescription());
            assertNotNull(entityFromDb.getExternalLink());
            assertNotNull(entityFromDb.getVisible());
            assertNotNull(entityFromDb.getPosition());
            assertNotNull(entityFromDb.getPrice());
            assertNotNull(entityFromDb.getQuantityStock());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
            assertNotNull(entityFromDb.getVersion());
        }
        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IProduct entity = saveNewProduct();
        getProductService().delete(entity.getId());
        assertNull(getProductService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewProduct();
        getProductService().deleteAll();
        assertEquals(0, getProductService().getAll().size());
    }

}
