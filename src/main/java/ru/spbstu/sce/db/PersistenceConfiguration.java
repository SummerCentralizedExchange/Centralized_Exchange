package ru.spbstu.sce.db;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {
    @Bean
    public ImplicitNamingStrategy namingStrategy() {
        return new SpringImplicitNamingStrategy();
    }
}
