package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Instrumento;
import java.util.List;

@Repository
public interface InstrumentoRepository extends JpaRepository<Instrumento,Integer>{
       
    List<Instrumento> findByTipoId(Integer tipoId);
    
}
