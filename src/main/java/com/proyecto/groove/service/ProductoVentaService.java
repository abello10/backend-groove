package com.proyecto.groove.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.proyecto.groove.model.ProductoVenta;
import com.proyecto.groove.repository.ProductoVentaRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
@SuppressWarnings("null")
public class ProductoVentaService {

    @Autowired
    private ProductoVentaRepository productoVentaRepository;

        
    public List<ProductoVenta> findAll(){
        return productoVentaRepository.findAll();
    }

    public ProductoVenta findById(Integer id){
        ProductoVenta productoVenta = productoVentaRepository.findById(id).orElse(null);
        return productoVenta;
    }

    public ProductoVenta save(ProductoVenta productoVenta){
        return productoVentaRepository.save(productoVenta);
    }

    public ProductoVenta partialUpdate(ProductoVenta productoVenta){
        ProductoVenta existingProductoVenta = productoVentaRepository.findById(productoVenta.getId()).orElse(null);
        if (existingProductoVenta != null){
            if(productoVenta.getCantidad() != null){
                existingProductoVenta.setCantidad(productoVenta.getCantidad());
            }

            if(productoVenta.getProducto() != null){
                existingProductoVenta.setProducto(productoVenta.getProducto());
            }

            if(productoVenta.getVenta() != null){
                existingProductoVenta.setVenta(productoVenta.getVenta());
            }

            return productoVentaRepository.save(existingProductoVenta);  
        }
        return null;
    }

    public void deleteById(Integer id) {
        productoVentaRepository.deleteById(id);
    }


    public void deleteByProductoId(Integer productoId){
        List<ProductoVenta> productosVentas = productoVentaRepository.findAll();
        for (ProductoVenta productoVenta : productosVentas){
            if (productoVenta.getProducto() != null && productoVenta.getProducto().getId().equals(productoId)){
                productoVentaRepository.deleteById(productoVenta.getId());
            }
        }
    }

    public void deleteByVentaId(Integer ventaId){
        List<ProductoVenta> productosVentas = productoVentaRepository.findAll();
        for (ProductoVenta productoVenta : productosVentas){
            if (productoVenta.getVenta() != null && productoVenta.getVenta().getId().equals(ventaId)){
                productoVentaRepository.deleteById(productoVenta.getId());
            }
        }
    }

    public List<ProductoVenta> findByProductoId(Integer productoId){
        return productoVentaRepository.findByProductoId(productoId);
    }

    public List<ProductoVenta> findByVentaId(Integer ventaId){
        return productoVentaRepository.findByVentaId(ventaId);
    }
    

    
}
