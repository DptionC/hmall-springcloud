package com.hmall.api.client.fallback;

import com.hmall.api.client.CartClient;
import com.hmall.common.exception.BizIllegalException;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;

/**
 * @Autor：林建威
 * @DateTime：2024/5/19 17:47
 **/
public class CartClientFallback implements FallbackFactory<CartClient> {
    @Override
    public CartClient create(Throwable cause) {
        return new CartClient() {
            @Override
            public void removeByItemIds(Collection<Long> ids) {
                throw new BizIllegalException(cause);
            }
        };
    }
}
