package ua.lviv.javaclub.javacaching.service;

import ua.lviv.javaclub.javacaching.domain.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User createOrReturnCached(User user);

    User createAndRefresh(User user);

    User create(String login, String name);

    User get(Long id);

    List<User> getAll();

    void delete(Long id);

    void deleteAndEvict(Long id);

}
