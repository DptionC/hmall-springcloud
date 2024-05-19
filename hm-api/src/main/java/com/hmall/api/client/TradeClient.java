package com.hmall.api.client;

import com.hmall.api.client.fallback.TradeClientFallback;
import com.hmall.api.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Autor：林建威
 * @DateTime：2024/5/15 8:49
 **/

@FeignClient(value = "trade-service",
        configuration = DefaultFeignConfig.class,
        fallbackFactory = TradeClientFallback.class)
public interface TradeClient {

    @PutMapping("/orders/{orderId}")
    public void markOrderPaySuccess(@PathVariable("orderId") Long orderId);
}
