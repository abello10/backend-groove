package com.proyecto.groove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.repository.CategoriaRepository;
import com.proyecto.groove.model.Categoria;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("null")
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TipoService tipoService;

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria findById(Integer id){
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        return categoria;
    }

    public Categoria save(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public Categoria partialUpdate(Categoria categoria){
        Categoria existingCategoria = categoriaRepository.findById(categoria.getId()).orElse(null);
        if (existingCategoria != null){
            if (categoria.getNombre() != null){
                existingCategoria.setNombre(categoria.getNombre());
            }
            return categoriaRepository.save(existingCategoria);
        }
        return null;
    }

    public void deleteById(Integer id){
        tipoService.deleteByCategoriaId(id);
        categoriaRepository.deleteById(id);
    }

    


    
}
