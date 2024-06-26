package com.hmall.api.config;

import com.hmall.api.client.TradeClient;
import com.hmall.api.client.fallback.*;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

/**
 * @Autor：林建威
 * @DateTime：2024/5/13 17:04
 **/
public class DefaultFeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor UserInfoRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Long userId = UserContext.getUser();
                if (userId != null) {
                    requestTemplate.header("user-info", userId.toString());
                }
            }
        };
    }

    @Bean
    public ItemClientFallback itemClientFallback() {
        return new ItemClientFallback();
    }

    @Bean
    public UserClientFallback userClientFallback() {
        return new UserClientFallback();
    }

    @Bean
    public CartClientFallback cartClientFallback() {
        return new CartClientFallback();
    }

    @Bean
    public TradeClientFallback tradeClientFallback() {
        return new TradeClientFallback();
    }

    @Bean
    public PayClientFallback payClientFallback() {
        return new PayClientFallback();
    }
}
