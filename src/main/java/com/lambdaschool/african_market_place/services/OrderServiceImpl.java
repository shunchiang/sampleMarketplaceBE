package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.Listing;
import com.lambdaschool.african_market_place.models.Order;
import com.lambdaschool.african_market_place.models.OrderItem;
import com.lambdaschool.african_market_place.models.User;
import com.lambdaschool.african_market_place.repositories.ListingRepository;
import com.lambdaschool.african_market_place.repositories.OrderRepository;
import com.lambdaschool.african_market_place.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().iterator().forEachRemaining(orders::add);
        return orders;
    }

    @Override
    public List<Order> findOrdersByUser(Long userid) {
        List<Order> orders = new ArrayList<>();
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("Hmm... we can't seem to find User " + userid + "."));
        for (Order o : user.getOrders())
        {
            orders.add(o);
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws
            ResourceNotFoundException
    {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry, it seems as though order " + orderId + " doesn't exist."));
        return order;
    }

    @Override
    public Order save(Order order) throws
            ResourceNotFoundException
    {
        Order newOrder = new Order();

        if (order.getOrdercode() != 0)
        {
            orderRepository.findById(order.getOrdercode())
                    .orElseThrow(() -> new ResourceNotFoundException("Order  " + order.getOrdercode() + " not found!"));
            newOrder.setOrdercode(order.getOrdercode());
        }

        newOrder.setLocation(order.getLocation());
        newOrder.setUser(order.getUser());

        for(OrderItem o : order.getOrderItems())
        {
            Order addOrder = orderService.findOrderById(o.getOrder().getOrdercode());
            newOrder.getOrderItems().add(new OrderItem(addOrder, o.getListing(), o.getQuantity()));
        }

        return orderRepository.save(newOrder);
    }

//    @Override
//    public Order update(Order order, Long ordercode) {
//        return null;
//    }

    @Override
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }
}
