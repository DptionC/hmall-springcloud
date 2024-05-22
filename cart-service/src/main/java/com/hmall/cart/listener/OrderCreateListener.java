package com.hmall.cart.listener;

import com.hmall.cart.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Autor：林建威
 * @DateTime：2024/5/20 9:28
 **/

@Component
@RequiredArgsConstructor
public class OrderCreateListener {

    private final ICartService cartService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "cart.clear.queue"),
            exchange = @Exchange(name = "trade.topic",type = ExchangeTypes.TOPIC),
            key = "order.create"))
    public void listenOrderCreate(List<Long> ids) {
        cartService.removeByItemIds(ids);
    }
}