package com.hmall.api.client.fallback;

import com.hmall.api.client.UserClient;
import com.hmall.common.exception.BizIllegalException;
import com.hmall.common.exception.UnauthorizedException;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Autor：林建威
 * @DateTime：2024/5/19 17:49
 **/
public class UserClientFallback implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public void deductMoney(String pw, Integer amount) {
                throw new UnauthorizedException(cause);
            }
        };
    }
}
