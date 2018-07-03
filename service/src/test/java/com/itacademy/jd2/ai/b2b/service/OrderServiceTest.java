package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderObject;

public class OrderServiceTest extends AbstractTest {

    @Before
    public void cleanTables() {
        getOrderItemService().deleteAll();
        getOrderService().deleteAll();
        getUserService().deleteAll();
    }

    @Test
    public void testCreate() {

        final IOrderObject entity = getOrderService().createEntity();
        entity.setClose(getRandomBoolean());
        entity.setCreator(saveNewUser()); // creator call user
        getOrderService().save(entity);

        final IOrderObject entityFromDb = getOrderService().getFullinfo(entity.getId());

        assertEquals(entity.getCreator().getId(), entityFromDb.getCreator().getId());
        assertNotNull(entityFromDb.getId());
        assertNotNull(entityFromDb.getClose());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        final IOrderObject entity = saveNewOrder();
        final IOrderObject entityFromDB = getOrderService().get(entity.getId());
        final Boolean newPrepayment = getRandomBoolean();
        entityFromDB.setClose(newPrepayment);
        getOrderService().save(entityFromDB);
        Thread.sleep(2);

        final IOrderObject udpatedEntityFromDB = getOrderService()
                .get(entityFromDB.getId());
        assertEquals(newPrepayment, udpatedEntityFromDB.getClose());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getOrderService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewOrder();
        }

        final List<IOrderObject> allEntities = getOrderService().getAll();

        for (final IOrderObject entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getClose());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
        }

        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IOrderObject entity = saveNewOrder();
        getOrderService().delete(entity.getId());
        assertNull(getOrderService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewOrder();
        getOrderService().deleteAll();
        assertEquals(0, getOrderService().getAll().size());
    }

}
