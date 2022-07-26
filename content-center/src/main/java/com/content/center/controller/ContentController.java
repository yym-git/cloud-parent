package com.content.center.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ym.y
 * @description
 * @date 22:05 2022/7/26
 */
@Slf4j
@RestController
public class ContentController {
    @GetMapping("/test/getNameAndAge/{name}/{age}")
    public String getInfo(@PathVariable("name") String name, @PathVariable("age") Integer age){
        log.info("content-center请求参数：{}，{}",name,age);
        return "姓名："+name+",年龄："+age ;
    }
    @GetMapping("/test/getNameAndAge2")
    public String getInfo2(@RequestParam("name") String name, @RequestParam("age") Integer age){
        log.info("content-center请求参数：{}，{}",name,age);
        return "姓名："+name+",年龄："+age ;
    }


}
