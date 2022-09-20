package me.hoon.orderservice.repository;


import me.hoon.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

     Order findByOrderId(String orderId);
     Iterable<Order> findByUserId(String userId);
}
