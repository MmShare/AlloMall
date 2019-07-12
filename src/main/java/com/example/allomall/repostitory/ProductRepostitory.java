package com.example.allomall.repostitory;

import com.example.allomall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepostitory extends JpaRepository<Product,Long> {

    List<Product> findProductsByState(Integer state);

    List<Product> findProductsByTid(Integer tid);

    Product findProductById(Integer id);

    List<Product> findProductsByNameLike(String name);

    List<Product> findProductsBySerialNumber(String number);

    @Transactional
    void deleteProductById(Integer id);
}
