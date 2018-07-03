package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.IOrderItem;

public class OrderItemServiceTest extends AbstractTest {

    @Before
    public void cleanTables() {
        getOrderItemService().deleteAll();
        getOrderService().deleteAll();
        getUserService().deleteAll();
        getProductService().deleteAll();
    }

    @Test
    public void testCreate() {

        final IOrderItem entity = getOrderItemService().createEntity();
        entity.setQuantity(getRandomObjectsCount());
        entity.setProduct(saveNewProduct());
        entity.setOrderObject(saveNewOrder());
        getOrderItemService().save(entity);

        final IOrderItem entityFromDb = getOrderItemService().get(entity.getId());

        assertEquals(entity.getOrderObject().getId(),
                entityFromDb.getOrderObject().getId());
        assertEquals(entity.getProduct().getId(), entityFromDb.getProduct().getId());
        assertNotNull(entityFromDb.getId());
        assertEquals(entity.getQuantity(), entityFromDb.getQuantity());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        final IOrderItem entity = saveNewOrderItem();
        final IOrderItem entityFromDB = getOrderItemService().get(entity.getId());
        final Integer newQuantity = getRandomObjectsCount();
        entityFromDB.setQuantity(newQuantity);
        getOrderItemService().save(entityFromDB);
        Thread.sleep(2);

        final IOrderItem udpatedEntityFromDB = getOrderItemService()
                .get(entityFromDB.getId());
        assertEquals(newQuantity, udpatedEntityFromDB.getQuantity());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getOrderItemService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewOrderItem();
        }

        final List<IOrderItem> allEntities = getOrderItemService().getAll();

        for (final IOrderItem entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getQuantity());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
        }

        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IOrderItem entity = saveNewOrderItem();
        getOrderItemService().delete(entity.getId());
        assertNull(getOrderItemService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewOrderItem();
        getOrderItemService().deleteAll();
        assertEquals(0, getOrderItemService().getAll().size());
    }

}
