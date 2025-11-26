package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.MusicaControllerV2;

import com.proyecto.groove.model.Musica;

@Component
public class MusicaModelAssembler implements RepresentationModelAssembler<Musica,EntityModel<Musica>>{

    @Override
    public EntityModel<Musica> toModel(Musica musica){
        return EntityModel.of(musica,
             linkTo(methodOn(MusicaControllerV2.class).getMusicaById(musica.getId())).withSelfRel(),
            linkTo(methodOn(MusicaControllerV2.class).getAllMusica()).withRel("musica"),
            linkTo(methodOn(MusicaControllerV2.class).updateMusica(musica.getId(),musica)).withRel("actualizar"),
            linkTo(methodOn(MusicaControllerV2.class).deleteMusica(musica.getId())).withRel("eliminar"),
            linkTo(methodOn(MusicaControllerV2.class).partialUpdateMusica(musica.getId(), musica)).withRel("reemplazar una línea")
           
           
        );
    }
}
