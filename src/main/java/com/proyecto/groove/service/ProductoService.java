package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.ProductoRepository;
import com.proyecto.groove.model.Producto;
import jakarta.transaction.Transactional;
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
        return productoRepository.save(producto);
    }

    public Producto partialUpdate(Producto producto){
        Producto existingProducto = productoRepository.findById(producto.getId()).orElse(null);
        if (existingProducto != null){
            if(producto.getPrecio() != null){
                existingProducto.setPrecio(producto.getPrecio());
            }

            if(producto.getAccesorio() != null){
                existingProducto.setAccesorio(producto.getAccesorio());
            }

            if(producto.getInstrumento() != null){
                existingProducto.setInstrumento(producto.getInstrumento());
            }

            if(producto.getMusica() != null){
                existingProducto.setMusica(producto.getMusica());
            }

            if(producto.getStock() != null){
                existingProducto.setStock(producto.getStock());
            }

            if(producto.getDescripcion() != null){
                existingProducto.setDescripcion(producto.getDescripcion());
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


    public void deleteByAccesorioId(Integer accesorioId){
        List<Producto> productos = productoRepository.findAll();
        for (Producto producto : productos){
            if (producto.getAccesorio() != null && producto.getAccesorio().getId().equals(accesorioId)){
                productoRepository.deleteById(producto.getId());
            }
        }
    }

    public void deleteByInstrumentoId(Integer instrumentoId){
        List<Producto> productos = productoRepository.findAll();
        for (Producto producto : productos){
            if (producto.getInstrumento() != null && producto.getInstrumento().getId().equals(instrumentoId)){
                productoRepository.deleteById(producto.getId());
            }
        }
    }

    public void deleteByMusicaId(Integer musicaId){
        List<Producto> productos = productoRepository.findAll();
        for (Producto producto : productos){
            if (producto.getMusica() != null && producto.getMusica().getId().equals(musicaId)){
                productoRepository.deleteById(producto.getId());
            }
        }
    }

   

    public List<Producto> findByAccesorioId(Integer accesorioId){
        return productoRepository.findByAccesorioId(accesorioId);
    }

    public List<Producto> findByInstrumentoId(Integer instrumentoId){
        return productoRepository.findByInstrumentoId(instrumentoId);
    }

    public List<Producto> findByMusicaId(Integer musicaId){
        return productoRepository.findByMusicaId(musicaId);
    }

    




}
