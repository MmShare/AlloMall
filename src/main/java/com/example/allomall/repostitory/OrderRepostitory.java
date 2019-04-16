package com.example.allomall.repostitory;

import com.example.allomall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepostitory extends JpaRepository<Order,Long> {
}
