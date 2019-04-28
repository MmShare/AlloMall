package com.example.allomall.repostitory;

import com.example.allomall.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepostitory extends JpaRepository<Material,Long> {

    Material findMaterialById(Integer id);
}
