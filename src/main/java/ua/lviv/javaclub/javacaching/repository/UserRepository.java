package ua.lviv.javaclub.javacaching.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.javaclub.javacaching.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
