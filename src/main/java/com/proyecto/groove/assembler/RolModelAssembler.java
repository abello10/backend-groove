package com.proyecto.groove.assembler;


import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.RolControllerV2;

import com.proyecto.groove.model.Rol;

@Component
public class RolModelAssembler implements RepresentationModelAssembler<Rol,EntityModel<Rol>>{

    @Override
    public EntityModel<Rol> toModel(Rol rol){
        return EntityModel.of(rol,
            linkTo(methodOn(RolControllerV2.class).getRolById(rol.getId())).withSelfRel(),
            linkTo(methodOn(RolControllerV2.class).getAllRoles()).withRel("roles"),
            linkTo(methodOn(RolControllerV2.class).updateRol(rol.getId(),rol)).withRel("actualizar"),
            linkTo(methodOn(RolControllerV2.class).deleteRol(rol.getId())).withRel("eliminar"),
            linkTo(methodOn(RolControllerV2.class).partialUpdateRol(rol.getId(), rol)).withRel("reemplazar una línea")
           
           
        );
    }
}
