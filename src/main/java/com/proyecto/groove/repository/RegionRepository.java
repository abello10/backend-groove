package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer>{

    
}
