package com.trailerplan.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
@Slf4j
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public MariadbConnectionFactory connectionFactory() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        log.debug("Active Spring Profile : " + activeProfile);
        System.out.println("Active Spring Profile : " + activeProfile);
        try (InputStream f = loader.getResourceAsStream("application-" + activeProfile + ".properties")) {
            props.load(f);
            return new MariadbConnectionFactory(MariadbConnectionConfiguration.builder()
                    .host(props.getProperty("database.host"))
                    .port(Integer.parseInt(props.getProperty("database.port")))
                    .username(props.getProperty("spring.r2dbc.username"))
                    .password(props.getProperty("spring.r2dbc.password"))
                    .database(props.getProperty("spring.r2dbc.name"))
                    .build());
        }
        catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
