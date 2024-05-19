package com.hmall.api.client.fallback;

import com.hmall.api.client.TradeClient;
import com.hmall.api.client.UserClient;
import com.hmall.common.exception.BizIllegalException;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Autor：林建威
 * @DateTime：2024/5/19 17:38
 **/
public class TradeClientFallback implements FallbackFactory<TradeClient> {
    @Override
    public TradeClient create(Throwable cause) {
        return new TradeClient() {
            @Override
            public void markOrderPaySuccess(Long orderId) {
                // 订单状态修改需要触发事务回滚，修改失败，抛出异常
                throw new BizIllegalException(cause);
            }
        };
    }
}
