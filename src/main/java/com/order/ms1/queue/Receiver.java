package com.order.ms1.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.ms1.dto.OrderDTO;
import com.order.ms1.entity.ItemEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    OrderDTO orderDTO = null;

    public void receiveMessage(String message) throws JsonProcessingException, InterruptedException {

    }
}