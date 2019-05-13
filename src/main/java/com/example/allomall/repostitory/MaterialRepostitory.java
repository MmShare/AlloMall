package com.example.allomall.repostitory;

import com.example.allomall.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MaterialRepostitory extends JpaRepository<Material,Long> {

    Material findMaterialById(Integer id);

    Material findMaterialByMtidAndId(Integer mtid,Integer id);

    @Transactional
    void deleteMaterialById(Integer id);

    List<Material> findMaterialsByNameContaining(String name);
}
