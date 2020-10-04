package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.Order;
import com.lambdaschool.african_market_place.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();
    List<Order> findOrdersByUser(Long userid);
    Order findOrderById(Long orderid);
    Order save(Order order);
//    Order update(Order order, Long ordercode);
    void deleteOrderById(Long orderId);
    void deleteAllOrders();
}
