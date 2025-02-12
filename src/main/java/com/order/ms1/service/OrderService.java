package com.order.ms1.service;

import com.order.ms1.dto.OrderDTO;
import com.order.ms1.entity.CacheEntity;
import com.order.ms1.entity.OrderEntity;
import com.order.ms1.repository.CacheRepository;
import com.order.ms1.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public OrderEntity createOrder(OrderDTO orderDTO) throws Exception {
        if(cacheRepository.findByDocument(orderDTO.toString()) != null){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Este pedido está sendo processado."
            );
        }
        CacheEntity cacheEntity = cacheRepository.save(new CacheEntity(orderDTO));
        orderDTO.setOrderId(cacheEntity.getId());
        OrderEntity processingOrder = orderRepository.save(new OrderEntity(orderDTO));
        rabbitTemplate.convertAndSend("spring-boot-exchange", "foo.bar.baz", orderDTO.toString());
        return processingOrder;
    }

    public List<OrderEntity> retrieveOrders(){
        return orderRepository.findAll();
    }
}
