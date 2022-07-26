package com.user.center.controller;

import cn.hutool.core.lang.hash.Hash;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.user.center.entity.BrokerMessage;
import com.user.center.mapper.BrokerMessageMapper;
import com.user.center.remote.ContentClient;
import com.user.center.service.BrokerMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ym.y
 * @description
 * @date 17:25 2022/6/14
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class TestController {
    @Value("${server.port}")
    private String port;
    @Autowired
    private BrokerMessageMapper brokerMessageMapper;
    @Autowired
    private ContentClient contentClient;

    @GetMapping("/hello")
    public String hello() {
        log.info("用户中心的测试接口");
        return "Hello user center!" + port;
    }

    @GetMapping("/page/{num}")
    public Page<BrokerMessage> pageSelect(@PathVariable("num") Integer num) {
        Page page = new Page(num, 10);
        Page<BrokerMessage> result = brokerMessageMapper.selectPage(page, null);
        log.info("分页查询结果:{}", result);
        return result;
    }

    @GetMapping("/remote/content")
    public String testRemote(@RequestParam("name") String name,
                             @RequestParam("age") Integer age) {
//        return contentClient.getNameAndAge(name, age);
        log.info("请求参数：{}，{}",name,age);
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("age", age);
        return contentClient.getNameAndAgeObj(param);
    }
}
