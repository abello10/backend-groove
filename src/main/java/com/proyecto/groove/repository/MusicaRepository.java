package com.proyecto.groove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Musica;

@Repository
public interface MusicaRepository extends JpaRepository<Musica,Integer>{

}
