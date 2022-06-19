package com.gateway.demo.expredicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author ym.y
 * @description 自定义谓词工厂，类名必须以PredicateFactory结尾
 * @date 22:16 2022/6/14
 */
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenRoutePredicateFactory.TimeBetweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                final LocalTime now = LocalTime.now();
                return now.isAfter(config.getStart()) && now.isBefore(config.getEnd());
            }
        };
    }

    /**
     * 映射配置属性和对象属性的关系
     *
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("start", "end");
    }

    @Validated
    public static class TimeBetweenConfig {
        private LocalTime start;
        private LocalTime end;
        public TimeBetweenConfig() {
        }

        public TimeBetweenConfig(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
        }

        public LocalTime getStart() {
            return start;
        }

        public void setStart(LocalTime start) {
            this.start = start;
        }

        public LocalTime getEnd() {
            return end;
        }

        public void setEnd(LocalTime end) {
            this.end = end;
        }
    }
}
