package com.itacademy.jd2.ai.b2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.itacademy.jd2.ai.b2b.dao.api.model.IUser;
import com.itacademy.jd2.ai.b2b.dao.api.model.enums.UserRole;

public class UserServiceTest extends AbstractTest {

    @Before
    public void cleanTables() {
        getOrderService().deleteAll();
        getUserService().deleteAll();
    }

    @Test
    public void testCreate() {
        final IUser entity = saveNewUser();

        final IUser entityFromDb = getUserService().get(entity.getId());

        assertEquals(entity.getName(), entityFromDb.getName());
        assertNotNull(entityFromDb.getId());
        assertEquals(entity.getEmail(), entityFromDb.getEmail());
        assertEquals(entity.getPassword(), entityFromDb.getPassword());
        assertNotNull(entityFromDb.getEnabled());
        assertNotNull(entityFromDb.getRole());
        assertNotNull(entityFromDb.getCreated());
        assertNotNull(entityFromDb.getUpdated());
        assertTrue(entityFromDb.getCreated().equals(entityFromDb.getUpdated()));
    }

    @Test
    public void testUpdate() throws InterruptedException {
        final IUser entity = saveNewUser();
        final IUser entityFromDB = getUserService().get(entity.getId());
        final String newName = "new-name-" + getRandomPrefix();
        final String newEmail = "new-email-" + getRandomPrefix();
        final String newPassword = "new-password-" + getRandomPrefix();
        final UserRole newRole = getRandomUserRole();
        entityFromDB.setName(newName);
        entityFromDB.setEmail(newEmail);
        entityFromDB.setPassword(newPassword);
        entityFromDB.setRole(newRole);
        getUserService().save(entityFromDB);
        Thread.sleep(2);

        final IUser udpatedEntityFromDB = getUserService().get(entityFromDB.getId());
        assertEquals(newName, udpatedEntityFromDB.getName());
        assertEquals(newEmail, udpatedEntityFromDB.getEmail());
        assertEquals(newPassword, udpatedEntityFromDB.getPassword());
        assertEquals(newRole, udpatedEntityFromDB.getRole());
        assertEquals(entity.getCreated(), udpatedEntityFromDB.getCreated());
        assertTrue(entity.getUpdated()
                .getTime() <= (udpatedEntityFromDB.getUpdated().getTime()));
    }

    @Test
    public void testGetAll() {
        final int intialCount = getUserService().getAll().size();

        final int randomObjectsCount = getRandomObjectsCount();
        for (int i = 0; i < randomObjectsCount; i++) {
            saveNewUser();
        }

        final List<IUser> allEntities = getUserService().getAll();

        for (final IUser entityFromDb : allEntities) {
            assertNotNull(entityFromDb.getName());
            assertNotNull(entityFromDb.getEmail());
            assertNotNull(entityFromDb.getPassword());
            assertNotNull(entityFromDb.getEnabled());
            assertNotNull(entityFromDb.getRole());
            assertNotNull(entityFromDb.getId());
            assertNotNull(entityFromDb.getCreated());
            assertNotNull(entityFromDb.getUpdated());
        }

        assertEquals(randomObjectsCount + intialCount, allEntities.size());
    }

    @Test
    public void testDelete() {
        final IUser entity = saveNewUser();
        getUserService().delete(entity.getId());
        assertNull(getUserService().get(entity.getId()));
    }

    @Test
    public void testDeleteAll() {
        saveNewUser();
        getUserService().deleteAll();
        assertEquals(0, getUserService().getAll().size());
    }

    @Test
    public void testEnterEmail() {

        final IUser entity = saveNewUser("mail@mail.com");
        final IUser currentUser = getUserService().enterEmail(entity.getEmail());
        assertEquals("mail@mail.com", currentUser.getEmail());

    }

    private IUser saveNewUser(final String mail) {
        final IUser user = saveNewUser();

        user.setName("new-name-" + getRandomPrefix());
        user.setEmail(mail);
        user.setPassword("new-password-" + getRandomPrefix());
        user.setRole(getRandomUserRole());

        getUserService().save(user);
        return user;
    }

}
