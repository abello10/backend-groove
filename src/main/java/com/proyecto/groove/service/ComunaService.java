package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.ComunaRepository;
import java.util.List;
import com.proyecto.groove.model.Comuna;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private DireccionService direccionService;
    
    public List<Comuna> findAll(){
        return comunaRepository.findAll();
    }

    public Comuna findById(Integer id){
        Comuna comuna = comunaRepository.findById(id).orElse(null);
        return comuna;
    }

    public Comuna save(Comuna comuna){
        return comunaRepository.save(comuna);
    }

    public Comuna partialUpdate(Comuna comuna){
        Comuna existingComuna = comunaRepository.findById(comuna.getId()).orElse(null);
        if(existingComuna != null){
            if (comuna.getRegion() != null){
                existingComuna.setRegion(comuna.getRegion());
            }
            if(comuna.getNombre() != null){
                existingComuna.setNombre(comuna.getNombre());
            }
            return comunaRepository.save(existingComuna);
        }
        return null;
    }

    public void deleteById(Integer id) {
        direccionService.deleteByComunaId(id);
        comunaRepository.deleteById(id);
    }

    public void deleteByRegionId(Integer regionId) {
        List<Comuna> comunas = comunaRepository.findAll();
        for (Comuna comuna : comunas) {
            if (comuna.getRegion() != null && comuna.getRegion().getId().equals(regionId)) {
                comunaRepository.deleteById(comuna.getId());
            }
        }
    }

    public List<Comuna> findByRegionId(Integer regionId) {
        return comunaRepository.findByRegionId(regionId);
    }


}
