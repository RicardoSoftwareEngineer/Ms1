package com.order.ms1.controller;

import com.order.ms1.dto.OrderDTO;
import com.order.ms1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/order/v1/create")
    public Object createOrder(@RequestBody OrderDTO orderDTO) throws Exception {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping(value = "/order/v1/retrieve")
    public Object retrieveOrders(){
        return orderService.retrieveOrders();
    }
}
