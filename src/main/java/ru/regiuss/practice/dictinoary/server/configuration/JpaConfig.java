package ru.regiuss.practice.dictinoary.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class JpaConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        Properties properties = System.getProperties();
        Map<String, Object> configOverrides = new HashMap<>();
        for (String name : properties.stringPropertyNames()) {
            if (name.contains("DB_PASS")) {
                configOverrides.put("javax.persistence.jdbc.password", properties.get(name));
            }
        }
        return Persistence.createEntityManagerFactory("mysql", configOverrides);
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
}
