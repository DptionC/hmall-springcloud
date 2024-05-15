package com.hmall.gateway.filter;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Autor：林建威
 * @DateTime：2024/5/15 11:27
 **/

@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    //spring提供的一个路径匹配器
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求
        ServerHttpRequest request = exchange.getRequest();
        //2.判断请求路径是否在排除范围内
        if (isExclude(request.getPath().toString())) {
            //如果在排除范围内的路径，直接放行
            return chain.filter(exchange);
        }
        //3.获取token
        String token = null;
        List<String> headers = request.getHeaders().get("authorization");
        if (headers != null && !headers.isEmpty()) {
            //集合不为空
            token = headers.get(0);
        }
        //4.校验并解析token
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token); //自带了判断
        } catch (UnauthorizedException e) {
            //如果不拦截，前端抛出的就是500，不能符合实际的响应状态码为401
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //表示完结了，不往后走了，其下的拦截器也不会执行。
            return response.setComplete();
        }
        //5.TODO 传递用户信息
        System.out.println("当前userId = " + userId);
        //6.放行
        return chain.filter(exchange);
    }

    private boolean isExclude(String path) {
        //使用循环遍历出所有排除的认证配置的路径
        List<String> excludePaths = authProperties.getExcludePaths();
        for (String pathPattern : excludePaths) {
            //依次表示每一个排除路径
            if (antPathMatcher.match(pathPattern, path)) {
                //如果一样返回true
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
