package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.MetodoEnvioRepository;
import com.proyecto.groove.model.MetodoEnvio;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("null")
public class MetodoEnvioService {
    @Autowired
    private MetodoEnvioRepository metodoEnvioRepository;

    @Autowired
    private VentaService ventaService;

    public List<MetodoEnvio> findAll() {
        return metodoEnvioRepository.findAll();
    }

    public MetodoEnvio findById(Integer id) {
        MetodoEnvio metodoEnvio = metodoEnvioRepository.findById(id).orElse(null);
        return metodoEnvio;
    }

    public MetodoEnvio save(MetodoEnvio metodoEnvio) {
        return metodoEnvioRepository.save(metodoEnvio);
    }

    public MetodoEnvio partialUpdate(MetodoEnvio metodoEnvio){
        MetodoEnvio existingMetodoEnvio = metodoEnvioRepository.findById(metodoEnvio.getId()).orElse(null);
        if (existingMetodoEnvio != null) {
            if (metodoEnvio.getNombre() != null) {
                existingMetodoEnvio.setNombre(metodoEnvio.getNombre());
            }
            

            return metodoEnvioRepository.save(existingMetodoEnvio);
        }
        return null;
    }

    public void deleteById(Integer id) {
        ventaService.deleteByMetodoEnvioId(id);
        metodoEnvioRepository.deleteById(id);
    }
}
