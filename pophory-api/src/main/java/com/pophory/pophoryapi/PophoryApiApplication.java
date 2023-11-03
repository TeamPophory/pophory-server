package com.pophory.pophoryapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pophory.pophoryapi", "com.pophory.pophorydomain",
        "com.pophory.pophorycommon", "com.pophory.pophoryexternal"})
@EnableJpaRepositories(basePackages = {"com.pophory.pophorydomain"})
@EntityScan(basePackages = {"com.pophory.pophorydomain"})
public class PophoryApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PophoryApiApplication.class, args);
    }
}
