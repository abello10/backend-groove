package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.DireccionControllerV2;

import com.proyecto.groove.model.Direccion;

@Component
public class DireccionModelAssembler implements RepresentationModelAssembler<Direccion,EntityModel<Direccion>>{

    @Override
    public EntityModel<Direccion> toModel(Direccion direccion){
        return EntityModel.of(direccion,
            linkTo(methodOn(DireccionControllerV2.class).getDireccionById(direccion.getId())).withSelfRel(),
            linkTo(methodOn(DireccionControllerV2.class).getAllDirecciones()).withRel("direcciones"),
            linkTo(methodOn(DireccionControllerV2.class).updateDireccion(direccion.getId(),direccion)).withRel("actualizar"),
            linkTo(methodOn(DireccionControllerV2.class).deleteDireccion(direccion.getId())).withRel("eliminar"),
            linkTo(methodOn(DireccionControllerV2.class).partialUpdateDireccion(direccion.getId(), direccion)).withRel("reemplazar una línea")
           
        );
    }
}
