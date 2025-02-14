package com.order.ms1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.ms1.dto.OrderDTO;
import com.order.ms1.entity.CacheEntity;
import com.order.ms1.entity.ItemEntity;
import com.order.ms1.entity.OrderEntity;
import com.order.ms1.entity.OrderStatus;
import com.order.ms1.repository.CacheRepository;
import com.order.ms1.repository.OrderRepository;
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

    @Mock
    private CacheRepository mockCacheRepository;

    @Mock
    private CacheRepository cacheRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private TestUtils testUtils = new TestUtils();

    private OrderDTO orderDTO;
    private OrderEntity orderEntity;
    private ItemEntity item1, item2;

    @BeforeEach
    void setUp() throws JsonProcessingException {

    }

    @Test
    void test_duplicity_avoidance() throws Exception {
        CacheEntity savedEntity = new CacheEntity(testUtils.createSampleOrder()); // Simulando uma entidade salva
        when(cacheRepository.findByDocument(savedEntity.getDocument())).thenReturn(savedEntity);
        orderDTO = testUtils.createSampleOrder();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            orderEntity = orderService.createOrder(orderDTO);
        });
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals("Este pedido está sendo processado.", exception.getReason());
    }

    @Test
    void test_order_disponibility() throws Exception {
        orderService.clearCache();
        orderDTO = testUtils.createSampleOrder();
        OrderEntity orderCreated = orderService.createOrder(orderDTO);
        List<OrderEntity> orders = orderService.retrieveOrders();
        for(OrderEntity orderEntity: orders){
            if(orderCreated.getId().equals(orderEntity.getId())){
                assertEquals(orderCreated.getStatus(), OrderStatus.PROCESSING);
                assertEquals(orderCreated.getTotalPrice(), 0.0);
            }
        }
    }

    @Test
    void test_microservice_contract() throws Exception {

    }
}
