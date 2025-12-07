package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.ProductoControllerV2;

import com.proyecto.groove.model.Producto;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto,EntityModel<Producto>>{

    @Override
    public EntityModel<Producto> toModel(Producto producto){
        return EntityModel.of(producto,
             linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getAllProducto()).withRel("productos"),
            linkTo(methodOn(ProductoControllerV2.class).updateProducto(producto.getId(),producto)).withRel("actualizar"),
            linkTo(methodOn(ProductoControllerV2.class).deleteProducto(producto.getId())).withRel("eliminar"),
            linkTo(methodOn(ProductoControllerV2.class).partialUpdateProducto(producto.getId(), producto)).withRel("reemplazar una línea")
           
           
        );
    }
}
