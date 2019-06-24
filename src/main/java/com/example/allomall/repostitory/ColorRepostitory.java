package com.example.allomall.repostitory;

import com.example.allomall.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ColorRepostitory extends JpaRepository<Color,Long> {

    Color  findColorById(Integer id);

    List<Color> findColorsByNameContaining(String name);

    @Transactional
    void deleteColorById(Integer id);
}
