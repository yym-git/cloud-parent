package com.gateway.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ym.y
 * @description 动态更新路由网关
 * 1、实现Spring提供的事件推送接口ApplicationEventPublisherAware
 * @date 18:20 2022/6/17
 */
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    //可以获取所有的路由信息
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * @description 删除路由
     * @author ym.y
     * @date 18:26 2022/6/17
     */
    public String deleteRoute(String routeId) {
        try {
            log.info("gateway delete route id: {}", routeId);
//            this.routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
            try {
                //TODO 能删除，但是抛出异常，为找到解决方法
                this.routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
            } catch (Exception e) {
            }
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }
    }

    /**
     * @description 更新路由信息：
     * 如果缓存中存在路由信息，则先删除路由信息
     * @author ym.y
     * @date 18:32 2022/6/17
     */
    public String updateList(List<RouteDefinition> definitions) {
        log.info("--------------gateway update route------------ {}", definitions);
        //如果缓存存在，删除缓存routedefinition
        List<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        log.info("获取缓存路由信息DynamicRouteServiceImpl#updateList：{}", routeDefinitions);
        if (!CollectionUtils.isEmpty(routeDefinitions)) {
            log.info("存在路由缓存，删除缓存..............");
            routeDefinitions.forEach(routeDefinition -> {
                log.info("delete cache routeDefinition:{}", routeDefinition);
                deleteRoute(routeDefinition.getId());
            });
        }
        definitions.forEach(routeDefinition -> {
            updateById(routeDefinition);
        });
        return "success";
    }

    public String updateById(RouteDefinition definition) {
        //在保存
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }
    }

    public String addRoute(RouteDefinition definition) {
        log.info("gateway add route {}", definition);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    public void addRouteList(List<RouteDefinition> definitions) {
        log.info("将从nacos获取到的路由信息存入到routeDefinitionWriter中......");
        for (RouteDefinition definition : definitions) {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        }
    }
}
