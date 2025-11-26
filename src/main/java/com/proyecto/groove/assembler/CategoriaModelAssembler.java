package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.CategoriaControllerV2;

import com.proyecto.groove.model.Categoria;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria,EntityModel<Categoria>>{

    @Override
    public EntityModel<Categoria> toModel(Categoria categoria){
        return EntityModel.of(categoria,
            linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(categoria.getId())).withSelfRel(),
            linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withRel("categorias"),
            linkTo(methodOn(CategoriaControllerV2.class).updateCategoria(categoria.getId(),categoria)).withRel("actualizar"),
            linkTo(methodOn(CategoriaControllerV2.class).deleteCategoria(categoria.getId())).withRel("eliminar"),
            linkTo(methodOn(CategoriaControllerV2.class).partialUpdateCategoria(categoria.getId(), categoria)).withRel("reemplazar una línea")
           
        );
    }
}