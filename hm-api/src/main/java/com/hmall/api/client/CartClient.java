package com.hmall.api.client;

import com.hmall.api.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @Autor：林建威
 * @DateTime：2024/5/14 17:42
 **/

@FeignClient(value = "cart-service")
public interface CartClient {

    @DeleteMapping("/carts")
    void removeByItemIds(@RequestParam("ids") Collection<Long> ids);
}
