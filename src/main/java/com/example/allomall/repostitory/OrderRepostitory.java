package com.example.allomall.repostitory;

import com.example.allomall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepostitory extends JpaRepository<Order,Long> {

    List<Order> findOrdersByState(String state);

    List<Order> findOrdersByNameLike(String name);

    Order findOrderById(Integer id);

    @Transactional
    void deleteOrderById(Integer id);


}
