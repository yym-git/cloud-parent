package com.gateway.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ym.y
 * @description
 * @date 18:09 2022/6/17
 */
@Data
@Configuration
public class GatewayConfig {
    public static final long DEFAULT_TIMEOUT = 30000;
    @Value("${nacos.init.server-addr}")
    public static String NACOS_SERVER_ADDR="localhost:8849";
    @Value("${nacos.init.namespace:''}")
    public static String NACOS_NAMESPACE;
    @Value("${nacos.init.data-id}")
    public static String NACOS_ROUTE_DATA_ID="gateway-json-routes";
    @Value("${nacos.init.sgroup}")
    public static String NACOS_ROUTE_GROUP="DEFAULT_GROUP";
}
