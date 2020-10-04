package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.OrderItem;
import com.lambdaschool.african_market_place.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "orderItemService")
public class OrderItemServiceImpl implements OrderItemService
{
    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem oi1) {
        OrderItem newOrderItem = new OrderItem();


        if (oi1.getOrderitemid() != 0)
        {
            orderItemRepository.findById(oi1.getOrderitemid())
            .orElseThrow(() -> new ResourceNotFoundException("Order item " + oi1.getOrderitemid() + " not found!"));
            newOrderItem.setOrderitemid(oi1.getOrderitemid());
        }

        newOrderItem.setOrder(oi1.getOrder());
        newOrderItem.setQuantity(oi1.getQuantity());
        newOrderItem.setListing(oi1.getListing());

        return orderItemRepository.save(newOrderItem);
    }
}
