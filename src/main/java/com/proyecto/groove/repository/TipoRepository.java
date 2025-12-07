package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Tipo;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<Tipo,Integer>{

        List<Tipo> findByCategoriaId(Integer categoriaId);

}
