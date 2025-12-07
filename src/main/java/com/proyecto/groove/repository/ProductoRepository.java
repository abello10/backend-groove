package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import com.proyecto.groove.model.Producto;




import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>{
        
        List<Producto> findByTipoId(Integer tipo);

        List<Producto> findByNombre(String nombre);
    
}
