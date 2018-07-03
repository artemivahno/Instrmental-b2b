package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.IBrand;

public class BrandServiceTest extends AbstractTest {

    @Before
    public void cleanTables() {
        getOrderItemService().deleteAll();
        getProductService().deleteAll();
        getCategoryService().deleteAll();
        getBrandService().deleteAll();
        getImageService().deleteAll();
    }

    @Test
    public void testCreate() {

        final IBrand entity = getBrandService().createEntity();
        entity.setName("name - " + getRandomPrefix());
        entity.setDescription("description - " + getRandomPrefix());
        entity.setImage(saveNewImage());
        getBrandService().save(entity);

        final IBrand entityFromDb = getBrandService().get(entity.getId());

        assertEquals(entity.getImage().getId(), entityFromDb.getImage().getId());
        assertEquals(entity.getName(), entityFromDb.getName());
        assertNotNull(entityFromDb.getId());
        assertEquals(entity.getDescription(), entityFromDb.getDescription());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        final IBrand entity = saveNewBrand();
        final IBrand entityFromDB = getBrandService().get(entity.getId());
        final String newName = "new-name-" + getRandomPrefix();
        final String newDescription = "new-Description-" + getRandomPrefix();
        entityFromDB.setName(newName);
        entityFromDB.setDescription(newDescription);
        getBrandService().save(entityFromDB);
        Thread.sleep(2);

        final IBrand udpatedEntityFromDB = getBrandService().get(entityFromDB.getId());
        assertEquals(newName, udpatedEntityFromDB.getName());
        assertEquals(newDescription, udpatedEntityFromDB.getDescription());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getBrandService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewBrand();
        }

        final List<IBrand> allEntities = getBrandService().getAll();

        for (final IBrand entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getName());
            assertNotNull(entityFromDb.getDescription());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
        }

        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IBrand entity = saveNewBrand();
        getBrandService().delete(entity.getId());
        assertNull(getBrandService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewBrand();
        getBrandService().deleteAll();
        assertEquals(0, getBrandService().getAll().size());
    }

}
