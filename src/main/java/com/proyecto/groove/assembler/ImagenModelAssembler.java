package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.ImagenControllerV2;

import com.proyecto.groove.model.Imagen;

@Component
public class ImagenModelAssembler implements RepresentationModelAssembler<Imagen,EntityModel<Imagen>>{

    @Override
    public EntityModel<Imagen> toModel(Imagen imagen){
        return EntityModel.of(imagen,
            linkTo(methodOn(ImagenControllerV2.class).getImagenById(imagen.getId())).withSelfRel(),
            linkTo(methodOn(ImagenControllerV2.class).getAllImagenes()).withRel("imagenes"),
            linkTo(methodOn(ImagenControllerV2.class).updateImagen(imagen.getId(),imagen)).withRel("actualizar"),
            linkTo(methodOn(ImagenControllerV2.class).deleteImagen(imagen.getId())).withRel("eliminar"),
            linkTo(methodOn(ImagenControllerV2.class).partialUpdateImagen(imagen.getId(), imagen)).withRel("reemplazar una línea")
           
        );
    }
}
