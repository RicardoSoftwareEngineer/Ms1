package com.order.ms1;

import com.order.ms1.dto.OrderDTO;
import com.order.ms1.entity.ItemEntity;

public class TestUtils {
    public OrderDTO createSampleOrder(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientId("99999212-d4fa-9659-32c7-3d7c65e2a70a");
        ItemEntity item = new ItemEntity();
        item.setName("Product a");
        item.setQuantity(13);
        item.setPrice(10.00);
        orderDTO.getItems().add(item);
        item = new ItemEntity();
        item.setName("Product b");
        item.setQuantity(3);
        item.setPrice(20.00);
        orderDTO.getItems().add(item);
        item = new ItemEntity();
        item.setName("Product c");
        item.setQuantity(5);
        item.setPrice(30.00);
        orderDTO.getItems().add(item);
        return orderDTO;
    }
}
