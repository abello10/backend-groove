package com.proyecto.groove.assembler;


import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.UsuarioControllerV2;

import com.proyecto.groove.model.Usuario;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario,EntityModel<Usuario>>{

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
            linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuario.getId(),usuario)).withRel("actualizar"),
            linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuario.getId())).withRel("eliminar"),
            linkTo(methodOn(UsuarioControllerV2.class).partialUpdateUsuario(usuario.getId(), usuario)).withRel("reemplazar una línea")
           
           
        );
    }
}
