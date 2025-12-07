package com.proyecto.groove.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    
    Usuario findByCorreo(String correo);
    List<Usuario> findByDireccionId(Integer direccionId);
    List<Usuario> findByRolId(Integer rolId);

}
