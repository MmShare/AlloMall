package com.example.allomall.repostitory;

import com.example.allomall.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MaterialRepostitory extends JpaRepository<Material,Long> {

    Material findMaterialById(Integer id);

    @Transactional
    void deleteMaterialById(Integer id);
}
