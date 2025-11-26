package com.proyecto.groove.assembler;


import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.MetodoPagoControllerV2;

import com.proyecto.groove.model.MetodoPago;

@Component
public class MetodoPagoModelAssembler implements RepresentationModelAssembler<MetodoPago,EntityModel<MetodoPago>>{

    @Override
    public EntityModel<MetodoPago> toModel(MetodoPago metodoPago){
        return EntityModel.of(metodoPago,
            linkTo(methodOn(MetodoPagoControllerV2.class).getMetodoPagoById(metodoPago.getId())).withSelfRel(),
            linkTo(methodOn(MetodoPagoControllerV2.class).getAllMetodosPago()).withRel("metodos pago"),
            linkTo(methodOn(MetodoPagoControllerV2.class).updateMetodoPago(metodoPago.getId(),metodoPago)).withRel("actualizar"),
            linkTo(methodOn(MetodoPagoControllerV2.class).deleteMetodoPago(metodoPago.getId())).withRel("eliminar"),
            linkTo(methodOn(MetodoPagoControllerV2.class).partialUpdateMetodoPago(metodoPago.getId(), metodoPago)).withRel("reemplazar una línea")
           
        );
    }
}
