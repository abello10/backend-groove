package com.proyecto.groove.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.ProductoVenta;




@Repository
public interface ProductoVentaRepository extends JpaRepository<ProductoVenta,Integer>{
    List<ProductoVenta> findByProductoId(Integer productoId);
    
    List<ProductoVenta> findByVentaId(Integer ventaId);
}
