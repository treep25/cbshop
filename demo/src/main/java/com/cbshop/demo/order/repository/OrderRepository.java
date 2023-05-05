package com.cbshop.demo.order.repository;

import com.cbshop.demo.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Page<Order>> getOrdersByUserId(@Param("user_id") long user_id, Pageable pageable);
}
