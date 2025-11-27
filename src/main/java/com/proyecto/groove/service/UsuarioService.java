package com.proyecto.groove.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.groove.model.Direccion;
import com.proyecto.groove.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import com.proyecto.groove.model.Usuario;

@Service
@Transactional
@SuppressWarnings("null")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    @Autowired
    private VentaService ventaService;
    
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuario;
    }

    public Usuario login(Usuario usuario) {
    Usuario foundUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());
 
    if (foundUsuario != null &&  passwordEncoder.matches(usuario.getContrasena(), foundUsuario.getContrasena())) {
          return foundUsuario;
     }

     return null;
    }

    public Usuario save(Usuario usuario) {
        String passwordHasheada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passwordHasheada);
        return usuarioRepository.save(usuario);
    }

    public Usuario partialUpdate(Usuario usuario){
        Usuario existingUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
        if(existingUsuario != null){
            if (usuario.getDireccion() != null){
                existingUsuario.setDireccion(usuario.getDireccion());
            }
            if(usuario.getRol() != null){
                existingUsuario.setRol(usuario.getRol());
            }
            if(usuario.getNombre() != null){
                existingUsuario.setNombre(usuario.getNombre());
            }
            if(usuario.getApellido() != null){
                existingUsuario.setApellido(usuario.getApellido());
            }
            if(usuario.getCorreo() != null){
                existingUsuario.setCorreo(usuario.getCorreo());
            }
            if(usuario.getContrasena() != null) {
                existingUsuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    public void deleteById(Integer id) {
        ventaService.deleteByUsuarioId(id);
        usuarioRepository.deleteById(id);
    }

    public void deleteByRolId(Integer rolId) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() != null && usuario.getRol().getId().equals(rolId)) {
                usuarioRepository.deleteById(usuario.getId());
            }
        }
    }

     public void desasociarDireccion(Integer direccionId) {
    List<Usuario> usuarios = usuarioRepository.findAll();
    Direccion direccionToRemove = new Direccion();
    direccionToRemove.setId(direccionId);

    for (Usuario usuario : usuarios) {
        if (usuario.getDireccion().remove(direccionToRemove)) {
            usuarioRepository.save(usuario);
        }
    }
    
}
    public List<Usuario> findByDireccionId(Integer direccionId) {
        return usuarioRepository.findByDireccionId(direccionId);
    }

    public List<Usuario> findByRolId(Integer rolId) {
        return usuarioRepository.findByRolId(rolId);
    }

    public Usuario login(String correo, String contrasenaOg){
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null){
            throw new RuntimeException("Usuario no encontrado");
        }
        if (!passwordEncoder.matches(contrasenaOg, usuario.getContrasena())){
            throw new RuntimeException("Contraseña incorrecta");
        }
        return usuario;
    }

    
    
}
