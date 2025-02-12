package com.order.ms1.entity;

import com.order.ms1.dto.OrderDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class CacheEntity {
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Id
    private String id;
    @Lob
    private String document;

    public CacheEntity() {
    }

    public CacheEntity(OrderDTO orderDTO) {
        this.document = orderDTO.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
