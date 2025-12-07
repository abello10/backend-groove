package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Comuna;


import java.util.List;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna,Integer>{

    List<Comuna> findByRegionId(Integer regionId);
    
}
