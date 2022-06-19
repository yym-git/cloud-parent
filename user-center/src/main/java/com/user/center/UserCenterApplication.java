package com.user.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ym.y
 * @description
 * @date 16:12 2022/6/14
 */
@EnableFeignClients
@SpringBootApplication
@MapperScan(basePackages = "com.user.center.mapper")
public class UserCenterApplication {
    public static void main(String[] args) {
        new SpringApplication(UserCenterApplication.class).run(args);
    }
}
