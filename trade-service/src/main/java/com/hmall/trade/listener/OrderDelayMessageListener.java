package com.hmall.trade.listener;

import com.hmall.api.client.PayClient;
import com.hmall.api.client.fallback.PayClientFallback;
import com.hmall.api.dto.PayOrderDTO;
import com.hmall.trade.constants.MqConstants;
import com.hmall.trade.domain.po.Order;
import com.hmall.trade.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Autor：林建威
 * @DateTime：2024/5/21 17:26
 **/

@Component
@RequiredArgsConstructor
public class OrderDelayMessageListener {

    private final IOrderService orderService;
    private final PayClient payClient;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.DELAY_ORDER_QUEUE_NAME),
            exchange = @Exchange(name = MqConstants.DELAY_EXCHANGE_NAME, delayed = "true"),
            key = MqConstants.DELAY_ORDER_KEY
    ))
    public void listenOrderDelayMessage(Long orderId) {
        //1.查询订单
        Order order = orderService.getById(orderId);
        //2.检测订单状态，是否为已付款 1 为未付款
        if (order == null || order.getStatus() != 1) {
            //如果订单不存在或支付状态是已支付
            return;
        }
        //3.未付款，需要查询支付流水状态，需要远程调取pay-service
        PayOrderDTO payOrderDTO = payClient.queryPayOrderByBizOrderNo(orderId);
        //4.判断是否已付款
        if (payOrderDTO != null && payOrderDTO.getStatus() ==3) {
            //已付款，则修改订单状态
            orderService.markOrderPaySuccess(orderId);
        } else {
            //TODO 未付款，则取消订单，并且恢复商品
            orderService.cancelOrder(orderId);
        }

    }
}