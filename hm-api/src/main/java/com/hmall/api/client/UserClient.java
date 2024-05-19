package com.hmall.api.client;

import com.hmall.api.client.fallback.UserClientFallback;
import com.hmall.api.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Autor：林建威
 * @DateTime：2024/5/15 8:45
 **/

@FeignClient(value = "user-service",
        configuration = DefaultFeignConfig.class,
        fallbackFactory = UserClientFallback.class)
public interface UserClient {

    @PutMapping("/users/money/deduct")
    void deductMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount);
}
