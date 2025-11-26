package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.AccesorioControllerV2;
import com.proyecto.groove.model.Accesorio;

@Component
public class AccesorioModelAssembler implements RepresentationModelAssembler<Accesorio,EntityModel<Accesorio>>{

    @Override
    public EntityModel<Accesorio> toModel(Accesorio accesorio){
        return EntityModel.of(accesorio,
            linkTo(methodOn(AccesorioControllerV2.class).getAccesorioById(accesorio.getProductoId())).withSelfRel(),
            linkTo(methodOn(AccesorioControllerV2.class).getAllAccesorios()).withRel("accesorios"),
            linkTo(methodOn(AccesorioControllerV2.class).updateAccesorio(accesorio.getProductoId(),accesorio)).withRel("actualizar"),
            linkTo(methodOn(AccesorioControllerV2.class).deleteAccesorio(accesorio.getProductoId())).withRel("eliminar"),
            linkTo(methodOn(AccesorioControllerV2.class).partialUpdateAccesorio(accesorio.getProductoId(), accesorio)).withRel("reemplazar una línea")
        );
    }
}