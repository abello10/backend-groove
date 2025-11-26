package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Producto;




import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>{

        List<Producto> findByAccesorioId(Integer accesorioId);

        List<Producto> findByInstrumentoId(Integer instrumentoId);

        List<Producto> findByMusicaId(Integer musicaId);
    
}
