package com.proyecto.groove.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.model.MetodoPago;
import com.proyecto.groove.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class MetodoPagoService {

     @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private VentaService ventaService;

    public List<MetodoPago> findAll() {
        return metodoPagoRepository.findAll();
    }

    public MetodoPago findById(Integer id) {
        MetodoPago metodoPago = metodoPagoRepository.findById(id).orElse(null);
        return metodoPago;
    }

    public MetodoPago save(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public MetodoPago partialUpdate(MetodoPago metodoPago){
        MetodoPago existingMetodoPago = metodoPagoRepository.findById(metodoPago.getId()).orElse(null);
        if (existingMetodoPago != null) {
            if (metodoPago.getNombre() != null) {
                existingMetodoPago.setNombre(metodoPago.getNombre());
            }
            

            return metodoPagoRepository.save(existingMetodoPago);
        }
        return null;
    }

    public void deleteById(Integer id) {
        ventaService.deleteByMetodoEnvioId(id);
        metodoPagoRepository.deleteById(id);
    }
    
}
