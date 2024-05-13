package com.hmall.api.client;

import com.hmall.api.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @Autor：林建威
 * @DateTime：2024/5/13 16:08
 **/

@FeignClient(value = "item-service")
public interface ItemClient {

    @GetMapping("/items")
    List<ItemDTO> queryItemsByIds(@RequestParam("ids") Collection<Long> ids);//因为购物车的itemIds是set集合，所以用Collection
}