package ua.lviv.javaclub.javacaching.domain.cache;

import org.springframework.stereotype.Component;
import ua.lviv.javaclub.javacaching.domain.User;

import javax.cache.annotation.CacheInvocationParameter;
import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheKeyInvocationContext;
import javax.cache.annotation.GeneratedCacheKey;
import java.lang.annotation.Annotation;

@Component
public class UserKeyGenerator implements CacheKeyGenerator {

    @Override
    public GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext) {
        CacheInvocationParameter[] parameters = cacheKeyInvocationContext.getAllParameters();
        // Calculate a key based on parameters
        return new UserCacheKey((User)parameters[0].getValue());
    }

}
