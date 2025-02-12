package com.order.ms1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.ms1.dto.OrderDTO;
import com.order.ms1.entity.ItemEntity;
import com.order.ms1.entity.OrderEntity;
import com.order.ms1.entity.OrderStatus;
import com.order.ms1.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    private OrderDTO orderDTO;
    private ItemEntity item1, item2;

    @BeforeEach
    void setUp() throws JsonProcessingException {
    }

    @Test
    void test_duplicity_avoidance() throws Exception {
        orderDTO = new OrderDTO();
        orderDTO.setClientId("99999212-d4fa-9659-32c7-3d7c65e2a70a");
        ItemEntity item = new ItemEntity();
        item.setName("Product a");
        item.setQuantity(2);
        item.setPrice(10.00);
        orderDTO.getItems().add(item);
        item = new ItemEntity();
        item.setName("Product b");
        item.setQuantity(3);
        item.setPrice(20.00);
        orderDTO.getItems().add(item);
        item = new ItemEntity();
        item.setName("Product c");
        item.setQuantity(4);
        item.setPrice(30.00);
        orderDTO.getItems().add(item);

        // Primeira chamada deve ser bem-sucedida
        orderService.createOrder(orderDTO);

        // Segunda chamada deve lançar a exceção
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            orderService.createOrder(orderDTO);
        });

        // Verificando o status HTTP e a mensagem de erro
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Este pedido está sendo processado.", exception.getReason());
    }

    @Test
    void test_order_disponibility() throws Exception {
        orderDTO = new OrderDTO();
        orderDTO.setClientId("99999212-d4fa-9659-32c7-3d7c65e2a70a");
        ItemEntity item = new ItemEntity();
        item.setName("Product a");
        item.setQuantity(2);
        item.setPrice(10.00);
        orderDTO.getItems().add(item);
        item = new ItemEntity();
        item.setName("Product b");
        item.setQuantity(3);
        item.setPrice(20.00);
        orderDTO.getItems().add(item);
        item = new ItemEntity();
        item.setName("Product c");
        item.setQuantity(4);
        item.setPrice(30.00);
        orderDTO.getItems().add(item);

        OrderEntity order = orderService.createOrder(orderDTO);
        List<OrderEntity> orders = orderService.retrieveOrders();
        for(OrderEntity orderEntity: orders){
            if(orderEntity.getId().equals(order.getId())){
                assertEquals(orderEntity.getStatus(), OrderStatus.PROCESSING);
                assertEquals(orderEntity.getTotalPrice(), 0.0);
            }
        }
    }
}
