package com.order.ms1.entity;

import com.order.ms1.dto.OrderDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OrderTable")
public class OrderEntity {
    @Id
    private String id;
    private LocalDateTime creationDate;
    private OrderStatus status;
    private double totalPrice;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<ItemEntity> items = new ArrayList<>();

    public OrderEntity() {
    }

    public OrderEntity(OrderDTO orderDTO) {
        this.id = orderDTO.getOrderId();
        this.creationDate = LocalDateTime.now();
        this.status = OrderStatus.PROCESSING;
        this.items.addAll(orderDTO.getItems());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }
}
