package com.proyecto.groove.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.proyecto.groove.model.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen,Integer>{
    
        List<Imagen> findByProductoId(Integer productoId);

}
