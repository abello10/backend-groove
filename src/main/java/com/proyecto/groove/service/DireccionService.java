package com.proyecto.groove.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.DireccionRepository;

import jakarta.transaction.Transactional;
import com.proyecto.groove.model.Direccion;
@Service
@Transactional
@SuppressWarnings("null")
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    
    public List<Direccion> findAll(){
        return direccionRepository.findAll();
    }

    public Direccion findById(Integer id){
        Direccion direccion = direccionRepository.findById(id).orElse(null);
        return direccion;
    }

    public Direccion save(Direccion direccion){
        return direccionRepository.save(direccion);
    }

    public Direccion partialUpdate(Direccion direccion){
        Direccion existingDireccion = direccionRepository.findById(direccion.getId()).orElse(null);
        if(existingDireccion != null){
            if (direccion.getCalle() != null){
                existingDireccion.setCalle(direccion.getCalle());
            }
            if(direccion.getDeptocasa() != null){
                existingDireccion.setDeptocasa(direccion.getDeptocasa());
            }
            if(direccion.getNumero() != null){
                existingDireccion.setNumero(direccion.getNumero());
            }
            if(direccion.getComuna() != null){
                existingDireccion.setComuna(direccion.getComuna());
            }
            return direccionRepository.save(existingDireccion);
        }
        return null;
    }

    public void deleteById(Integer id) {
        direccionRepository.deleteById(id);
    }

    public void deleteByComunaId(Integer comunaId) {
        List<Direccion> direcciones = direccionRepository.findAll();
        for (Direccion direccion : direcciones) {
            if (direccion.getComuna() != null && direccion.getComuna().getId().equals(comunaId)) {
                direccionRepository.deleteById(direccion.getId());
            }
        }
    }
    

    public List<Direccion> findByComunaId(Integer comunaId) {
        return direccionRepository.findByComunaId(comunaId);
    }

    
    
}
