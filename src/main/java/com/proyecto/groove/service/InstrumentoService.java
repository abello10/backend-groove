package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.groove.repository.InstrumentoRepository;
import com.proyecto.groove.model.Instrumento;
import com.proyecto.groove.model.Producto;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InstrumentoService {

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private ProductoService productoService;

    public List<Instrumento> findAll(){
        return instrumentoRepository.findAll();
    }

    public Instrumento findById(Integer id){
        return instrumentoRepository.findById(id).orElse(null);
    }

    public Instrumento save(Instrumento instrumento){
        
        if (instrumento.getProducto() == null || instrumento.getProducto().getId() == null) {
            throw new RuntimeException("ERROR: No se puede crear un Instrumento sin asociarlo a un Producto (ID).");
        }
        Integer idPadre = instrumento.getProducto().getId();
        
        Producto padreReal = productoService.findById(idPadre);
        if (padreReal == null) {
             throw new RuntimeException("ERROR: El Producto con ID " + idPadre + " no existe.");
        }

        instrumento.setId(idPadre); 
        
        instrumento.setProducto(padreReal);

        return instrumentoRepository.save(instrumento);
    }
    // 👆 ---------------------------------- 👆

    public Instrumento partialUpdate(Instrumento instrumento){
        Instrumento existingInstrumento = instrumentoRepository.findById(instrumento.getId()).orElse(null);
        
        if (existingInstrumento != null){
            if(instrumento.getAnoFabricacion() != null) existingInstrumento.setAnoFabricacion(instrumento.getAnoFabricacion());
            if(instrumento.getTipo() != null) existingInstrumento.setTipo(instrumento.getTipo());
            if(instrumento.getMarca() != null) existingInstrumento.setMarca(instrumento.getMarca());
            if(instrumento.getModelo() != null) existingInstrumento.setModelo(instrumento.getModelo());
            
            return instrumentoRepository.save(existingInstrumento);
        }
        return null;
    }
    
    public void deleteById(Integer id){
        productoService.deleteByInstrumentoId(id);
        instrumentoRepository.deleteById(id);
    }

    public void deleteByTipoId(Integer tipoId){
        List<Instrumento> instrumentos = instrumentoRepository.findAll();
        for (Instrumento instrumento : instrumentos){
            if(instrumento.getTipo() != null && instrumento.getTipo().getId().equals(tipoId)){
                instrumentoRepository.deleteById(instrumento.getId());
            }
        }
    }

    public List<Instrumento> findByTipoId(Integer tipoId) {
        return instrumentoRepository.findByTipoId(tipoId);
    }
}