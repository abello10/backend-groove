package com.proyecto.groove.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.proyecto.groove.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import com.proyecto.groove.model.Rol;

@Service
@Transactional
@SuppressWarnings("null")
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol findById(Integer id) {
        Rol rol = rolRepository.findById(id).orElse(null);
        return rol;
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol partialUpdate(Rol rol){
        Rol existingRol = rolRepository.findById(rol.getId()).orElse(null);
        if (existingRol != null) {
            if (rol.getNombre() != null) {
                existingRol.setNombre(rol.getNombre());
            }

            return rolRepository.save(existingRol);
        }
        return null;
    }

    public void deleteById(Integer id) {
        usuarioService.deleteByRolId(id);
        rolRepository.deleteById(id);
    }
}
