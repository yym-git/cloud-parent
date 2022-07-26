package com.user.center.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ym.y
 * @description
 * @date 22:19 2022/7/26
 */
@FeignClient(value = "conetent-center",url = "http://127.0.0.1:8042")
public interface ContentClient {
    @GetMapping(value = "/test/getNameAndAge")
    String getNameAndAge(@PathVariable("name") String name,
                         @PathVariable("age") Integer age);

    @RequestMapping(value = "/test/getNameAndAge",method = RequestMethod.GET)
    String getNameAndAgeObj(@SpringQueryMap Map<String,Object> param);
}
