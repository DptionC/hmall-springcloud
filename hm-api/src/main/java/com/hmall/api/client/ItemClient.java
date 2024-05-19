package com.hmall.api.client;

import com.hmall.api.client.fallback.ItemClientFallback;
import com.hmall.api.config.DefaultFeignConfig;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @Autor：林建威
 * @DateTime：2024/5/13 16:08
 **/

@FeignClient(value = "item-service",
        configuration = DefaultFeignConfig.class,
        fallbackFactory = ItemClientFallback.class)
public interface ItemClient {

    @PutMapping("/items/stock/deduct")
    void deductStock(@RequestBody List<OrderDetailDTO> items);

    @GetMapping("/items")
    List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);//因为购物车的itemIds是set集合，所以用Collection
}
