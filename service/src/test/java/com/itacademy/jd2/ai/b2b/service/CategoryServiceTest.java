package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.ICategory;

public class CategoryServiceTest extends AbstractTest {

    @Before
    public void cleanTables() {
        getProductService().deleteAll();
        getCategoryService().deleteAll();
        getBrandService().deleteAll();
        getImageService().deleteAll();
    }

    @Test
    public void testCreate() {

        final ICategory entity = getCategoryService().createEntity();
        entity.setName("name - " + getRandomPrefix());
        entity.setDescription("description - " + getRandomPrefix());
        entity.setPosition(getRandomObjectsCount());
        entity.setImage(saveNewImage());
        getCategoryService().save(entity);

        final ICategory entityFromDb = getCategoryService().get(entity.getId());

        assertEquals(entity.getName(), entityFromDb.getName());
        assertNotNull(entityFromDb.getId());
        assertEquals(entity.getDescription(), entityFromDb.getDescription());
        assertEquals(entity.getPosition(), entityFromDb.getPosition());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));

    }

    @Test
    public void testUpdate() throws InterruptedException {
        final ICategory entity = saveNewCategory();
        final ICategory entityFromDB = getCategoryService().get(entity.getId());
        final String newName = "new-name-" + getRandomPrefix();
        final String newDescription = "new-description-" + getRandomPrefix();
        final Integer newPosition = getRandomObjectsCount();
        Thread.sleep(2);

        entityFromDB.setName(newName);
        entityFromDB.setDescription(newDescription);
        entityFromDB.setPosition(newPosition);
        getCategoryService().save(entityFromDB);

        final ICategory udpatedEntityFromDB = getCategoryService()
                .get(entityFromDB.getId());
        assertEquals(newName, udpatedEntityFromDB.getName());
        assertEquals(newDescription, udpatedEntityFromDB.getDescription());
        assertEquals(newPosition, udpatedEntityFromDB.getPosition());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getCategoryService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewCategory();
        }

        final List<ICategory> allEntities = getCategoryService().getAll();

        for (final ICategory entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getName());
            assertNotNull(entityFromDb.getDescription());
            assertNotNull(entityFromDb.getPosition());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
        }
        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final ICategory entity = saveNewCategory();
        getCategoryService().delete(entity.getId());
        assertNull(getCategoryService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewCategory();
        getCategoryService().deleteAll();
        assertEquals(0, getCategoryService().getAll().size());
    }

}
