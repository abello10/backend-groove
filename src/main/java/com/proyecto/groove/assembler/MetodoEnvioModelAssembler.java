package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.MetodoEnvioControllerV2;

import com.proyecto.groove.model.MetodoEnvio;

@Component
public class MetodoEnvioModelAssembler implements RepresentationModelAssembler<MetodoEnvio,EntityModel<MetodoEnvio>>{

    @Override
    public EntityModel<MetodoEnvio> toModel(MetodoEnvio metodoEnvio){
        return EntityModel.of(metodoEnvio,
            linkTo(methodOn(MetodoEnvioControllerV2.class).getMetodoEnvioById(metodoEnvio.getId())).withSelfRel(),
            linkTo(methodOn(MetodoEnvioControllerV2.class).getAllMetodosEnvio()).withRel("metodos envio"),
            linkTo(methodOn(MetodoEnvioControllerV2.class).updateMetodoEnvio(metodoEnvio.getId(),metodoEnvio)).withRel("actualizar"),
            linkTo(methodOn(MetodoEnvioControllerV2.class).deleteMetodoEnvio(metodoEnvio.getId())).withRel("eliminar"),
            linkTo(methodOn(MetodoEnvioControllerV2.class).partialUpdateMetodoEnvio(metodoEnvio.getId(), metodoEnvio)).withRel("reemplazar una línea")
           
        );
    }
}
