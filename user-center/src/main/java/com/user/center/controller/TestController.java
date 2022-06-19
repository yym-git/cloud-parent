package com.user.center.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.user.center.entity.BrokerMessage;
import com.user.center.mapper.BrokerMessageMapper;
import com.user.center.service.BrokerMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ym.y
 * @description
 * @date 17:25 2022/6/14
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class TestController {
    @Autowired
    private BrokerMessageMapper brokerMessageMapper;

    @GetMapping("/hello")
    public String hello() {
        log.info("用户中心的测试接口");
        return "Hello user center!";
    }

    @GetMapping("/page/{num}")
    public Page<BrokerMessage> pageSelect(@PathVariable("num") Integer num) {
        Page page = new Page(num, 10);
        Page<BrokerMessage> result = brokerMessageMapper.selectPage(page, null);
        log.info("分页查询结果:{}", result);
        return result;
    }
}
