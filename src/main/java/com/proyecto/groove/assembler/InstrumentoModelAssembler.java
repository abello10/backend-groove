package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.InstrumentoControllerV2;

import com.proyecto.groove.model.Instrumento;

@Component
public class InstrumentoModelAssembler implements RepresentationModelAssembler<Instrumento,EntityModel<Instrumento>>{

    @Override
    public EntityModel<Instrumento> toModel(Instrumento instrumento){
        return EntityModel.of(instrumento,
            linkTo(methodOn(InstrumentoControllerV2.class).getInstrumentoById(instrumento.getProductoId())).withSelfRel(),
            linkTo(methodOn(InstrumentoControllerV2.class).getAllInstrumentos()).withRel("instrumentos"),
            linkTo(methodOn(InstrumentoControllerV2.class).updateInstrumento(instrumento.getProductoId(),instrumento)).withRel("actualizar"),
            linkTo(methodOn(InstrumentoControllerV2.class).deleteInstrumento(instrumento.getProductoId())).withRel("eliminar"),
            linkTo(methodOn(InstrumentoControllerV2.class).partialUpdateInstrumento(instrumento.getProductoId(), instrumento)).withRel("reemplazar una línea")
           
        );
    }
}
