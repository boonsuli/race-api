package com.trailerplan.config;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.trailerplan.model.converters.LocalDateToDateConverter;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.trailerplan.repository", "com.trailerplan.service", "com.trailerplan.controller"})
@EnableR2dbcRepositories(basePackages = {"com.trailerplan.repository"})
@EnableTransactionManagement
@Slf4j
public class AppConfig {

    @Bean(name = "org.dozer.Mapper")
    public DozerBeanMapperFactoryBean dozerBean() throws IOException {
        DozerBeanMapperFactoryBean dozerBean = new DozerBeanMapperFactoryBean();
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:dozer/**/*.dzr.xml");
        dozerBean.setMappingFiles(resources);
        dozerBean.setCustomConverters(List.of(new LocalDateToDateConverter()));
        return dozerBean;
    }

    @Bean
    public UndertowServletWebServerFactory embeddedServletContainerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
            @Override
            public void customize(io.undertow.Undertow.Builder builder) {
                builder.addHttpListener(8081, "0.0.0.0");
            }
        });
        return factory;
    }
}