package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer>{
    
}
