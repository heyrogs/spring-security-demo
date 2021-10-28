package com.rog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.rog.mapper")
@SpringBootApplication
public class SpringSecurityServerJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityServerJwtApplication.class, args);
    }

}
