package com.course.kafka.broker.consumer;

import com.course.kafka.broker.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer
{
    @KafkaListener(topics = "t.commodity.order")
    public void consume(ConsumerRecord<String, OrderMessage> consumerRecord)
    {
        log.info("Consuming ConsumerRecord: {}", consumerRecord);
        int surpriseBonusPercentage = Integer.parseInt(new String(consumerRecord.headers().lastHeader("surpriseBonus").value()));

        OrderMessage message = consumerRecord.value();
        double surpriseBonusAmount = surpriseBonusPercentage * message.getPrice() * message.getQuantity() / 100.0;
        log.info("Surprise bonus amount for location {} is: {}", message.getOrderLocation(), surpriseBonusAmount);
    }
}
