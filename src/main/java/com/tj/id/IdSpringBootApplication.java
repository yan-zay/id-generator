package com.tj.id;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: zay
 * @Date: 2024-02-29 10:11
 */

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.tj.id.**",
        }
)
public class IdSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdSpringBootApplication.class, args);
    }
}
