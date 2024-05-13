package com.example.m183project.config;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlyWayConfig {
    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return new Flyway(Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(dataSource));
    }
}
