package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.ComunaControllerV2;

import com.proyecto.groove.model.Comuna;

@Component
public class ComunaModelAssembler implements RepresentationModelAssembler<Comuna,EntityModel<Comuna>>{

    @Override
    public EntityModel<Comuna> toModel(Comuna comuna){
        return EntityModel.of(comuna,
            linkTo(methodOn(ComunaControllerV2.class).getComunaById(comuna.getId())).withSelfRel(),
            linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withRel("comunas"),
            linkTo(methodOn(ComunaControllerV2.class).updateComuna(comuna.getId(),comuna)).withRel("actualizar"),
            linkTo(methodOn(ComunaControllerV2.class).deleteComuna(comuna.getId())).withRel("eliminar"),
            linkTo(methodOn(ComunaControllerV2.class).partialUpdateComuna(comuna.getId(), comuna)).withRel("reemplazar una línea")
           
        );
    }
}