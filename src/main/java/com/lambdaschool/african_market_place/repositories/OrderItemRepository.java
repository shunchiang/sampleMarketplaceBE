package com.lambdaschool.african_market_place.repositories;

import com.lambdaschool.african_market_place.models.Order;
import com.lambdaschool.african_market_place.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
