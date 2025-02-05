package com.example.orderservice.service;

import com.example.orderservice.dao.OrderDAO;
import com.example.orderservice.domain.*;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.producer.OrderStringProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final ModelMapper modelMapper;
    private final OrderStringProducer orderStringProducer;

    @Override
    @Transactional
    public void save(OrderRequestDTO orderEntity) {
        List<OrderProductEntity> list = orderEntity.getOrderproductlist().stream().
                map(product -> modelMapper.map(product, OrderProductEntity.class)).collect(Collectors.toList());
        OrderEntity order = OrderEntity.makeOrderEntity(orderEntity.getAddr(), Long.parseLong(orderEntity.getCustomerId()), list);
        orderDAO.save(order);

        //주문이 성공하면 주문한 정보를 product-service로 보내기
        //=> 주문정보를 하나씩 꺼내서 넘기는 작업 - 테스트
        //=> 주문정보를 한꺼번에 넘기기 - 미션
        for(OrderDetailDTO item : orderEntity.getOrderproductlist()){
            orderStringProducer.send(item);
            log.info("Sent order product to {}", item);
        }
    }

    @Override
    public OrderResponseDTO findById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> findAllByCustomerId(Long customerId) {
        return List.of();
    }
}
