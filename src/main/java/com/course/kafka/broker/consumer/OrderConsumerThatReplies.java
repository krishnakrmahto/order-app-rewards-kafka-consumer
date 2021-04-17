package com.course.kafka.broker.consumer;

import com.course.kafka.broker.message.OrderMessage;
import com.course.kafka.broker.message.OrderReplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumerThatReplies
{
    @KafkaListener(topics = "t.commodity.order")
    @SendTo("t.commodity.order-reply")
    public OrderReplyMessage consume(ConsumerRecord<String, OrderMessage> consumerRecord)
    {
        log.info("Consuming ConsumerRecord: {}", consumerRecord);
        int surpriseBonusPercentage = Integer.parseInt(new String(consumerRecord.headers().lastHeader("surpriseBonus").value()));

        OrderMessage message = consumerRecord.value();
        double surpriseBonusAmount = surpriseBonusPercentage * message.getPrice() * message.getQuantity() / 100.0;
        log.info("Surprise bonus amount for location {} is: {}", message.getOrderLocation(), surpriseBonusAmount);

        return OrderReplyMessage.builder()
                                .message("Bonus processed for orderNumber: " + message.getOrderNumber() + " at location: " +
                                         message.getOrderLocation() + ", " + "bonusPercentage: " + surpriseBonusPercentage +
                                         ", bonusAmount: " + surpriseBonusAmount)
                                .build();
    }
}
