package com.proyecto.groove.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Direccion;


@Repository
public interface DireccionRepository extends JpaRepository<Direccion,Integer> {
    
    List<Direccion> findByComunaId(Integer comunaId);


}
