package com.hmall.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Autor：林建威
 * @DateTime：2024/5/15 10:18
 **/

// @Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //TODO 模拟登录校验1.获取请求
        ServerHttpRequest request = exchange.getRequest();
        //2.获取请求头
        HttpHeaders headers = request.getHeaders();
        System.out.println("headers = " + headers);
        //3.放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        //设置过滤器执行顺序，值越小，优先级越高
        return 0;
    }
}
