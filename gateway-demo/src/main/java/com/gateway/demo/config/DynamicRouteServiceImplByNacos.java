package com.gateway.demo.config;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

@Component
@Slf4j
@DependsOn({"gatewayConfig"})
public class DynamicRouteServiceImplByNacos {
    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;
    private ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("gateway route init..");
        try {
            //初始化nacosConfig
            configService = initConfigService();
            //从nacos获取路由配置信息，并设置到缓存中
            initCache();
        } catch (Exception e) {
            log.info("初始化网关路由时发生错误:", e);
        }
        //添加nacos监听
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID, GatewayConfig.NACOS_ROUTE_GROUP);
    }


    /**
     * @description 监听Nacos下发的动态路由配置
     * @author ym.y
     * @date 18:56 2022/6/17
     */
    public void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            configService.addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("开始网关路由更新DynamicRouteServiceImplByNacos#dynamicRouteByNacosListener:\n\r{}", configInfo);
                    if(StringUtils.isNotBlank(configInfo)){
                        JSONArray jsonArray = JSONUtil.parseArray(configInfo);
                        List<RouteDefinition> routeDefinitions = JSONUtil.toList(jsonArray, RouteDefinition.class);
                        dynamicRouteService.updateList(routeDefinitions);
                    }
                }
            });
        } catch (NacosException e) {
            log.info("从nacos接收动态路由配置出错!!!", e);
        }
    }

    /**
     * @description 初始化网关路由
     * @author ym.y
     * @date 18:56 2022/6/17
     */
    private ConfigService initConfigService() {
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, GatewayConfig.NACOS_SERVER_ADDR);
            //配置了namespace时，添加以下属性
            //properties.put(PropertyKeyConst.NAMESPACE, GatewayConfig.NACOS_NAMESPACE);
            return NacosFactory.createConfigService(properties);
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误", e);
            return null;
        }
    }

    private void initCache() {
        log.info("从nacos配置文件中读取配置信息......................");
        try {
            String configInfo = configService.getConfig(GatewayConfig.NACOS_ROUTE_DATA_ID,
                    GatewayConfig.NACOS_ROUTE_GROUP, GatewayConfig.DEFAULT_TIMEOUT);
            JSONArray jsonArray = JSONUtil.parseArray(configInfo);
            if (!jsonArray.isEmpty()) {
                List<RouteDefinition> routeDefinitions = JSONUtil.toList(jsonArray, RouteDefinition.class);
                dynamicRouteService.addRouteList(routeDefinitions);
            }
        } catch (NacosException e) {
        }
    }
}
