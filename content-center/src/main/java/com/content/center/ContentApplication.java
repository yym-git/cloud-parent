package com.content.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ym.y
 * @description
 * @date 16:39 2022/6/14
 */
@EnableFeignClients
@SpringBootApplication
public class ContentApplication {
    public static void main(String[] args) {
        new SpringApplication(ContentApplication.class).run(args);
    }
}
