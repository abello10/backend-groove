package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.ProductoRepository;
import com.proyecto.groove.model.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("null")
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoVentaService productoVentaService;
    
    
    @Autowired
    private ImagenService imagenService;
    
    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Integer id){
        Producto producto = productoRepository.findById(id).orElse(null);
        return producto;
    }

    public Producto save(Producto producto){
        if (producto.getImagenes() != null){
            for (com.proyecto.groove.model.Imagen img : producto.getImagenes()){
                img.setProducto(producto);
            }
        }
        return productoRepository.save(producto);
    }

    public Producto partialUpdate(Producto producto){
        Producto existingProducto = productoRepository.findById(producto.getId()).orElse(null);
        if (existingProducto != null){

            if(producto.getNombre() != null){
                existingProducto.setNombre(producto.getNombre());
            }

            if(producto.getPrecio() != null){
                existingProducto.setPrecio(producto.getPrecio());
            }

            if(producto.getStock() != null){
                existingProducto.setStock(producto.getStock());
            }

            if(producto.getDescripcion() != null){
                existingProducto.setDescripcion(producto.getDescripcion());
            }

            if (producto.getImagenes() != null) {
                for (com.proyecto.groove.model.Imagen img : producto.getImagenes()) {
                    img.setProducto(existingProducto);
                }
                existingProducto.setImagenes(producto.getImagenes());
            }

            return productoRepository.save(existingProducto);  
        }
        return null;
    }

      public void deleteById(Integer id) {
      productoVentaService.deleteByProductoId(id); 
      imagenService.deleteByProductoId(id); 
      productoRepository.deleteById(id);
}
      public void deleteByTipoId(Integer tipoId){
        List<Producto> productos = productoRepository.findAll();
        for (Producto producto : productos){
            if(producto.getTipo() != null && producto.getTipo().getId().equals(tipoId)){
                productoRepository.deleteById(producto.getId());
            }
        }
    }

    public List<Producto> buscarPorNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }



    public List<Producto> buscarPorTipo(Integer tipo){
        return productoRepository.findByTipoId(tipo);
    }

    public List<Producto> OrdenarPrecio(String direccion){
        String campoOrden = "precio";

        Sort orden = direccion.equalsIgnoreCase("desc")?
                    Sort.by(campoOrden).descending():
                    Sort.by(campoOrden).ascending();
        return productoRepository.findAll(orden);
    }

    




}
