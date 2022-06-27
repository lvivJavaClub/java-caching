package ua.lviv.javaclub.javacaching.domain.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ua.lviv.javaclub.javacaching.domain.User;

import javax.cache.annotation.GeneratedCacheKey;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@ToString
public class UserCacheKey implements GeneratedCacheKey {

    @Getter
    private final User user;

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o instanceof UserCacheKey) {
            return user.getLogin().equals(
                Optional.ofNullable(((UserCacheKey)o).getUser()).map(User::getLogin).orElse(null)
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return user.getLogin().hashCode();
    }

}