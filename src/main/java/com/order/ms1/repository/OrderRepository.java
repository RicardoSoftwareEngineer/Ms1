package com.order.ms1.repository;

import com.order.ms1.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("jpaCacheRepository")
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Modifying
    @Transactional
    @Query("update OrderEntity o set o.totalPrice = :newTotal where o.id = :orderId")
    void updateTotalByOrderId(@Param("newTotal") double newTotal, @Param("orderId") String orderId);

    List<OrderEntity> findAll();
}
