package com.example.orderservice.service.producer;

import com.example.orderservice.domain.OrderDetailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//OrderDetailDTO 객체를 product-service로 보내기

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderStringProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(OrderDetailDTO order) {
        //매개변수로 전달 받은 DTO => String으로 변환
        String orderString = "";
        try {
            orderString = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //publish - 메시지 전송
        kafkaTemplate.send("dev.shop.order.create", orderString);
        log.info("=========================================");
        log.info("Sent order : {}", orderString);
        log.info("=========================================");
    }
}
