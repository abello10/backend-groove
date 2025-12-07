package com.proyecto.groove.assembler;


import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.TipoControllerV2;

import com.proyecto.groove.model.Tipo;

@Component
public class TipoModelAssembler implements RepresentationModelAssembler<Tipo,EntityModel<Tipo>>{

    @Override
    public EntityModel<Tipo> toModel(Tipo tipo){
        return EntityModel.of(tipo,
            linkTo(methodOn(TipoControllerV2.class).getTipoById(tipo.getId())).withSelfRel(),
            linkTo(methodOn(TipoControllerV2.class).getAllTipos()).withRel("tipos"),
            linkTo(methodOn(TipoControllerV2.class).updateTipo(tipo.getId(),tipo)).withRel("actualizar"),
            linkTo(methodOn(TipoControllerV2.class).deleteTipo(tipo.getId())).withRel("eliminar"),
            linkTo(methodOn(TipoControllerV2.class).partialUpdateTipo(tipo.getId(), tipo)).withRel("reemplazar una línea")
           
           
        );
    }
}
