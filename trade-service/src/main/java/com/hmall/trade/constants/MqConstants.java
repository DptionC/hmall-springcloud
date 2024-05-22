package com.hmall.trade.constants;

import org.stringtemplate.v4.ST;

/**
 * @Autor：林建威
 * @DateTime：2024/5/21 17:08
 **/

public interface MqConstants {
    String DELAY_EXCHANGE_NAME = "trade.delay.direct";
    String DELAY_ORDER_QUEUE_NAME = "trade.delay.order.queue";
    String DELAY_ORDER_KEY = "delay.order.query";
}
