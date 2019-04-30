package com.example.allomall.repostitory;

import com.example.allomall.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TypeRepostitory extends JpaRepository<Type, Long> {

    Type findTypeById(Integer id);

    List<Type> findTypeByNameContaining(String name);

    @Transactional
    void deleteTypeById(Integer id);
}
