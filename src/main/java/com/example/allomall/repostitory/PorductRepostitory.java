package com.example.allomall.repostitory;

import com.example.allomall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PorductRepostitory extends JpaRepository<Product,Long> {
}
