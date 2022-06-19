package com.gateway.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * version of Kotlin.
 * @author ym.y
 * @description
 * @date 15:40 2022/6/14
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        new SpringApplication(GatewayApplication.class).run(args);
    }
}
