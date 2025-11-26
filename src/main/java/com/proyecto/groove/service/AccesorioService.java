package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.AccesorioRepository;
import com.proyecto.groove.model.Accesorio;


import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("null")
public class AccesorioService {

    @Autowired
    private AccesorioRepository accesorioRepository;

    @Autowired
    private ProductoService productoService;

    public List<Accesorio> findAll(){
        return accesorioRepository.findAll();
    }

    public Accesorio findById(Integer id){
        Accesorio accesorio = accesorioRepository.findById(id).orElse(null);
        return accesorio;
    }

    public Accesorio save(Accesorio accesorio){
        return accesorioRepository.save(accesorio);
    }

    public Accesorio partialUpdate(Accesorio accesorio){
        Accesorio existingAccesorio = accesorioRepository.findById(accesorio.getProductoId()).orElse(null);
        if (existingAccesorio != null){

            if(accesorio.getMarca() != null){
                existingAccesorio.setMarca(accesorio.getMarca());
    
            }

            if(accesorio.getModelo() != null){
                existingAccesorio.setModelo(accesorio.getModelo());
            }

            return accesorioRepository.save(existingAccesorio);
        }
        return null;
    }

    public void deleteById(Integer id){
        productoService.deleteByAccesorioId(id);
        accesorioRepository.deleteById(id);
    }


    
}