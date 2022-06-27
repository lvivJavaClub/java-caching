package ua.lviv.javaclub.javacaching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JavaCachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaCachingApplication.class, args);
	}

}
