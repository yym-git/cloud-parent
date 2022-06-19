package com.gateway.demo.myfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author ym.y
 * @description 自定义过滤器
 * @date 0:13 2022/6/15
 */
@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return ((exchange, chain) -> {
            log.info("请求进来了，{},{}", config.getName(), config.getValue());
            ServerHttpRequest request = exchange.getRequest().mutate().build();
            ServerWebExchange build = exchange.mutate().request(request).build();
            return chain.filter(build);
        });
    }
}
