package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.IAttribute;

public class AttributeServiceTest extends AbstractTest {

    @Before
    public void cleanTables() {
        getAttributeService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IAttribute entity = saveNewAttribute();

        final IAttribute entityFromDb = getAttributeService().get(entity.getId());

        assertEquals(entity.getName(), entityFromDb.getName());
        assertNotNull(entityFromDb.getId());
        assertEquals(entity.getValue(), entityFromDb.getValue());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        final IAttribute entity = saveNewAttribute();
        final IAttribute entityFromDB = getAttributeService().get(entity.getId());
        final String newName = "new-name-" + getRandomPrefix();
        final String newValue = "new-value-" + getRandomPrefix();
        entityFromDB.setName(newName);
        entityFromDB.setValue(newValue);
        getAttributeService().save(entityFromDB);
        Thread.sleep(2);

        final IAttribute udpatedEntityFromDB = getAttributeService()
                .get(entityFromDB.getId());
        assertEquals(newName, udpatedEntityFromDB.getName());
        assertEquals(newValue, udpatedEntityFromDB.getValue());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getAttributeService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewAttribute();
        }

        final List<IAttribute> allEntities = getAttributeService().getAll();

        for (final IAttribute entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getName());
            assertNotNull(entityFromDb.getValue());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
        }

        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IAttribute entity = saveNewAttribute();
        getAttributeService().delete(entity.getId());
        assertNull(getAttributeService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewAttribute();
        getAttributeService().deleteAll();
        assertEquals(0, getAttributeService().getAll().size());
    }

}
