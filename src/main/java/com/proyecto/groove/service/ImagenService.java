package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.groove.model.Imagen;
import com.proyecto.groove.repository.ImagenRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("null")
public class ImagenService {

    @Autowired
    private ImagenRepository imagenRepository;

    public List<Imagen> findAll(){
        return imagenRepository.findAll();
    }

    public Imagen findById(Integer id){
        Imagen imagen = imagenRepository.findById(id).orElse(null);
        return imagen;
    }

    public Imagen save(Imagen imagen){
        return imagenRepository.save(imagen);
    }

    public Imagen partialUpdate(Imagen imagen){
        Imagen existingImagen = imagenRepository.findById(imagen.getId()).orElse(null);
        if (existingImagen != null){
            if (imagen.getProducto() != null){
                existingImagen.setProducto(imagen.getProducto());
            }

            if(imagen.getUrl() != null){
                existingImagen.setUrl(imagen.getUrl());
            }
            return imagenRepository.save(existingImagen);
        }
        return null;
    }

    public void deleteById(Integer id){
        imagenRepository.deleteById(id);
    }

    public void deleteByProductoId(Integer productoId){
        List<Imagen> imagenes = imagenRepository.findAll();
        for (Imagen imagen : imagenes){
            if (imagen.getProducto() != null && imagen.getProducto().getId().equals(productoId)){
                imagenRepository.deleteById(imagen.getId());
            }
        }
    }

    public List<Imagen> findByProductoId(Integer productoId){
        return imagenRepository.findByProductoId(productoId);
    }

    
    
}
