package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.IImage;

public class ImageServiceTest extends AbstractTest {

    @Before
    public void cleanTables() {
        getProductService().deleteAll();
        getCategoryService().deleteAll();
        getBrandService().deleteAll();
        getImageService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IImage entity = saveNewImage();

        final IImage entityFromDb = getImageService().get(entity.getId());

        assertEquals(entity.getName(), entityFromDb.getName());
        assertNotNull(entityFromDb.getId());
        assertEquals(entity.getUrl(), entityFromDb.getUrl());
        assertNotNull(entityFromDb.getPosition());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        final IImage entity = saveNewImage();
        final IImage entityFromDB = getImageService().get(entity.getId());
        final String newName = "new-name-" + getRandomPrefix();
        final String newUrl = "new-url-" + getRandomPrefix();
        final Integer newPosition = getRandomObjectsCount();
        entityFromDB.setName(newName);
        entityFromDB.setUrl(newUrl);
        entityFromDB.setPosition(newPosition);
        getImageService().save(entityFromDB);
        Thread.sleep(2);

        final IImage udpatedEntityFromDB = getImageService().get(entityFromDB.getId());
        assertEquals(newName, udpatedEntityFromDB.getName());
        assertEquals(newUrl, udpatedEntityFromDB.getUrl());
        assertEquals(newPosition, udpatedEntityFromDB.getPosition());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getImageService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewImage();
        }

        final List<IImage> allEntities = getImageService().getAll();

        for (final IImage entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getName());
            assertNotNull(entityFromDb.getUrl());
            assertNotNull(entityFromDb.getPosition());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
        }

        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IImage entity = saveNewImage();
        getImageService().delete(entity.getId());
        assertNull(getImageService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewImage();
        getImageService().deleteAll();
        assertEquals(0, getImageService().getAll().size());
    }

}
