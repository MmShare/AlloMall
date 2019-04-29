package com.example.allomall.repostitory;

import com.example.allomall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepostitory extends JpaRepository<Product,Long> {

    List<Product> findProductsByState(Integer state);

    List<Product> findProductsByTid(Integer tid);

    Product findProductById(Integer id);

    List<Product> findProductsByNameLike(String name);
}
