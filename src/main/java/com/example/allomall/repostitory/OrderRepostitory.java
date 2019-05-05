package com.example.allomall.repostitory;

import com.example.allomall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepostitory extends JpaRepository<Order,Long> {

    List<Order> findOrdersByState(String state);

    List<Order> findOrdersByOrderNumberContainingAndPeopleNameContainingAndPeopleAddressContaining(String orderNumber,String peopleName,String peopleAddress);

    List<Order> findOrdersByOrderNumberContainingOrPeopleNameContainingOrPeopleAddressContaining(String orderNumber,String peopleName,String peopleAddress);

    List<Order> findOrdersByOrderNumberContainingAndStateOrPeopleNameContainingOrPeopleAddressContaining(String orderNumber,String state,String peopleName,String peopleAddress);

    List<Order> findOrdersByStateOrPeopleAddressContainingOrPeopleNameContaining(String state,String address,String name);

    Order findOrderById(Integer id);

    @Transactional
    void deleteOrderById(Integer id);


}
