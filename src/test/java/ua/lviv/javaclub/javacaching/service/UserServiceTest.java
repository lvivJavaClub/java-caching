package ua.lviv.javaclub.javacaching.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.lviv.javaclub.javacaching.AbstractSpringApplicationTest;
import ua.lviv.javaclub.javacaching.domain.User;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class UserServiceTest extends AbstractSpringApplicationTest {

    @Autowired
    private UserService service;

    private void logGet(Long id) {
        log.info("User found: {}", service.get(id));
    }

    private void logCreate(String login, String name) { log.info("User created: {}", service.create(login, name)); }

    @Test
    public void get() {
        User user1 = service.create(new User("myko", "Mykola"));
        User user2 = service.create(new User("pet", "Petro"));

        // Should actually invoke get only twice
        logGet(user1.getId());
        logGet(user2.getId());
        logGet(user1.getId());
        logGet(user2.getId());
    }

    @Test
    public void create() {
        // Should create 2 users as we have 2 unique keys
        logCreate("ivan", "Ivan");
        logCreate("ivan", "Petro");
        logCreate("petro", "Petro");

        log.info("Users:");
        service.getAll().forEach(u -> log.info("{}", u.toString()));
    }

    @Test
    public void createAndRefresh() {
        User user1 = service.createOrReturnCached(new User("myko", "Mykola"));
        log.info("Created user 1: {}", user1);

        // Should skip invoking create method
        User user2 = service.createOrReturnCached(new User("myko", "Mykola Veresenj"));
        log.info("Created user 2: {}", user2);

        // Should invoke create method
        User user3 = service.createAndRefresh(new User("myko", "Koljan"));
        log.info("Created user 3: {}", user3);

        // Should skip invoking create method
        User user4 = service.createOrReturnCached(new User("myko", "Mykola Finalist"));
        log.info("Created user 4: {}", user4);
    }

    @Test
    public void delete() {
        User user1 = service.create(new User("myko", "Mykola"));
        log.info("{}", service.get(user1.getId()));

        User user2 = service.create(new User("myko", "Mykola"));
        log.info("{}", service.get(user2.getId()));

        // Delete from repository but not from cache
        service.delete(user1.getId());

        // Delete from repository and from cache
        service.deleteAndEvict(user2.getId());

        // Should return cached user that actually was deleted from repository
        log.info("{}", service.get(user1.getId()));

        // Should throw an exception as the 1st user was deleted without deleting it from cache
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
            log.info("{}", service.get(user2.getId()))
        );
    }

    @Test
    public void checkSettings() throws InterruptedException {
        User user1 = service.createOrReturnCached(new User("myko", "Mykola"));
        log.info("User 1: {}", service.get(user1.getId()));

        // Should skip invoking create method
        User user2 = service.createOrReturnCached(new User("myko", "Mykola"));
        log.info("User 2: {}", service.get(user2.getId()));

        log.info("Sleeping 3s...");
        Thread.sleep(3000L);

        // Should invoke create method as the previous item was evicted
        User user3 = service.createOrReturnCached(new User("myko", "Mykola"));
        log.info("User 3: {}", service.get(user3.getId()));
    }

}