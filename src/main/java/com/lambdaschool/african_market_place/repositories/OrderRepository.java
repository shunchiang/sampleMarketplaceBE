package com.lambdaschool.african_market_place.repositories;

import com.lambdaschool.african_market_place.models.Order;
import com.lambdaschool.african_market_place.models.User;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
//    User FindUserByOrderId(Long orderid);
//
//    Order FindOrderById(Long orderid);


}
