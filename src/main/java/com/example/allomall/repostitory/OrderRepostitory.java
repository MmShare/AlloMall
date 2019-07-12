package com.example.allomall.repostitory;

import com.example.allomall.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepostitory extends JpaRepository<Order,Long> {

    List<Order> findOrdersByState(String state, Sort sort);

    List<Order> findOrdersByStateOrPeopleAddressContainingOrPeopleNameContaining(String state,String address,String name,Sort sort);

    List<Order> findOrdersByIdIn(Integer[] ids,Sort sort);

    Order findOrderById(Integer id);

    @Transactional
    void deleteOrderById(Integer id);


}
