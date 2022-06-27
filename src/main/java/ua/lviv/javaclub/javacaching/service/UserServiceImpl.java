package ua.lviv.javaclub.javacaching.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ua.lviv.javaclub.javacaching.domain.User;
import ua.lviv.javaclub.javacaching.domain.cache.UserKeyGenerator;
import ua.lviv.javaclub.javacaching.repository.UserRepository;

import javax.cache.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String USERS_CACHE = "users";

    private final UserRepository repository;
    private final ApplicationContext context;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    @CacheResult(cacheName = USERS_CACHE, cacheKeyGenerator = UserKeyGenerator.class)
    public User createOrReturnCached(@CacheKey @CacheValue User user) {
        log.info("Creating user: {}", user);
        return repository.save(user);
    }

    @Override
    @CachePut(cacheName = USERS_CACHE, cacheKeyGenerator = UserKeyGenerator.class)
    public User createAndRefresh(@CacheKey @CacheValue User user) {
        log.info("Creating user: {}", user);
        return repository.save(user);
    }

    @Override
    @CacheResult(cacheName = USERS_CACHE)
    public User create(@CacheKey String login, String name) {
        log.info("Creating user with parameters: {}, {}", login, name);
        return repository.save(new User(login, name));
    }

    @Override
    @CacheResult(cacheName = USERS_CACHE)
    public User get(Long id) {
        log.info("Getting user by id: {}", id);
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found by id " + id));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    @CacheRemove(cacheName = USERS_CACHE)
    public void deleteAndEvict(Long id) {
        log.info("Deleting user by id: {}", id);
        repository.deleteById(id);
    }

}
