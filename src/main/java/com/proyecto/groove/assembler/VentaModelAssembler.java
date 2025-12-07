package com.proyecto.groove.assembler;


import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.VentaControllerV2;

import com.proyecto.groove.model.Venta;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta,EntityModel<Venta>>{

    @Override
    public EntityModel<Venta> toModel(Venta venta){
        return EntityModel.of(venta,
            linkTo(methodOn(VentaControllerV2.class).getVentaById(venta.getId())).withSelfRel(),
            linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withRel("ventas"),
            linkTo(methodOn(VentaControllerV2.class).updateVenta(venta.getId(),venta)).withRel("actualizar"),
            linkTo(methodOn(VentaControllerV2.class).deleteVenta(venta.getId())).withRel("eliminar"),
            linkTo(methodOn(VentaControllerV2.class).partialUpdateVenta(venta.getId(), venta)).withRel("reemplazar una línea")
           
           
        );
    }
}
