package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.TipoRepository;
import com.proyecto.groove.model.Tipo;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("null")
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private ProductoService productoService;

    public List<Tipo> findAll(){
        return tipoRepository.findAll();
    }

    public Tipo findById(Integer id){
        Tipo tipo = tipoRepository.findById(id).orElse(null);
        return tipo;
    }

    public Tipo save(Tipo tipo){
        return tipoRepository.save(tipo);
    }

    public Tipo partialUpdate(Tipo tipo){
        Tipo existingTipo = tipoRepository.findById(tipo.getId()).orElse(null);
        if (existingTipo != null){
            if (tipo.getCategoria() != null){
                existingTipo.setCategoria(tipo.getCategoria());
            }

            if(tipo.getNombre() != null){
                existingTipo.setNombre(tipo.getNombre());
            }
            return tipoRepository.save(existingTipo);
        }
        return null;
    }

    public void deleteByCategoriaId(Integer categoriaId) {
        List<Tipo> tipos = tipoRepository.findAll();
        for (Tipo tipo : tipos) {
            if (tipo.getCategoria() != null && tipo.getCategoria().getId().equals(categoriaId)) {
                tipoRepository.deleteById(tipo.getId());
            }
        }
    }

    public void deleteById(Integer id){
        productoService.deleteByTipoId(id);
        tipoRepository.deleteById(id);
    }

     public List<Tipo> findByCategoriaId(Integer categoriaId) {
        return tipoRepository.findByCategoriaId(categoriaId);
    }
    
}
