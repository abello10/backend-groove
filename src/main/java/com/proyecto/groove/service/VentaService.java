package com.proyecto.groove.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.proyecto.groove.repository.VentaRepository;
import com.proyecto.groove.repository.ProductoRepository; 
import com.proyecto.groove.model.Venta;
import com.proyecto.groove.model.Producto; 
import com.proyecto.groove.model.ProductoVenta;


@Service
@Transactional
@SuppressWarnings("null")
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoVentaService productoVentaService;
    
    
    public List<Venta> findAll(){
        return ventaRepository.findAll();
    }

    public Venta findById(Integer id){
        Venta venta = ventaRepository.findById(id).orElse(null);
        return venta;
    }

    public Venta save(Venta venta){
            if (venta.getFecha() == null){
            venta.setFecha(LocalDateTime.now());
        }

        
        if (venta.getProductos() != null) { 
            for (ProductoVenta detalle : venta.getProductos()) {
                
                Producto productoBD = productoRepository.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Error: Producto no encontrado (ID: " + detalle.getProducto().getId() + ")"));

                if (productoBD.getStock() < detalle.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para: " + productoBD.getNombre() + ". Disponible: " + productoBD.getStock());
                }

                int nuevoStock = productoBD.getStock() - detalle.getCantidad();
                productoBD.setStock(nuevoStock);

                productoRepository.save(productoBD);
            }
        }

        return ventaRepository.save(venta);
    }

    public Venta partialUpdate(Venta venta){
        Venta existingVenta = ventaRepository.findById(venta.getId()).orElse(null);
        if (existingVenta != null){
            if(venta.getTotal() != null){
                existingVenta.setTotal(venta.getTotal());
            }
            if(venta.getMetodoEnvio() != null){
                existingVenta.setMetodoEnvio(venta.getMetodoEnvio());
            }
            if(venta.getMetodoPago() != null){
                existingVenta.setMetodoPago(venta.getMetodoPago());
            }
            if(venta.getUsuario() != null){
                existingVenta.setUsuario(venta.getUsuario());
            }
            if(venta.getEstado() != null){
                existingVenta.setEstado(venta.getEstado());
            }

            return ventaRepository.save(existingVenta);  
        }
        return null;
    }

      public void deleteById(Integer id) {
      productoVentaService.deleteByVentaId(id); 
      ventaRepository.deleteById(id);
    }


    public void deleteByMetodoEnvioId(Integer metodoEnvioId){
        List<Venta> ventas = ventaRepository.findAll();
        for (Venta venta : ventas){
            if (venta.getMetodoEnvio() != null && venta.getMetodoEnvio().getId().equals(metodoEnvioId)){
                ventaRepository.deleteById(venta.getId());
            }
        }
    }

    public void deleteByMetodoPagoId(Integer metodoPagoId){
        List<Venta> ventas = ventaRepository.findAll();
        for (Venta venta : ventas){
            if (venta.getMetodoPago() != null && venta.getMetodoPago().getId().equals(metodoPagoId)){
                ventaRepository.deleteById(venta.getId());
            }
        }
    }

    public void deleteByUsuarioId(Integer usuarioId){
        List<Venta> ventas = ventaRepository.findAll();
        for (Venta venta : ventas){
            if (venta.getUsuario() != null && venta.getUsuario().getId().equals(usuarioId)){
                ventaRepository.deleteById(venta.getId());
            }
        }
    }

    

    public List<Venta> findByMetodoPagoId(Integer metodoPagoId){
        return ventaRepository.findByMetodoPagoId(metodoPagoId);
    }

    public List<Venta> findByMetodoEnvioId(Integer metodoEnvioId){
        return ventaRepository.findByMetodoEnvioId(metodoEnvioId);
    }

    public List<Venta> findByUsuarioId(Integer usuarioId){
        return ventaRepository.findByUsuarioId(usuarioId);
    }

}