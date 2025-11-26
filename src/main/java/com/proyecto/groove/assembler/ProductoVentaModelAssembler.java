package com.proyecto.groove.assembler;

import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.proyecto.groove.controller.V2.ProductoVentaControllerV2;

import com.proyecto.groove.model.ProductoVenta;

@Component
public class ProductoVentaModelAssembler implements RepresentationModelAssembler<ProductoVenta,EntityModel<ProductoVenta>>{

    @Override
    public EntityModel<ProductoVenta> toModel(ProductoVenta productoVenta){
        return EntityModel.of(productoVenta,
             linkTo(methodOn(ProductoVentaControllerV2.class).getProductoVentaById(productoVenta.getId())).withSelfRel(),
            linkTo(methodOn(ProductoVentaControllerV2.class).getAllProductoVenta()).withRel("venta de productos"),
            linkTo(methodOn(ProductoVentaControllerV2.class).updateProductoVenta(productoVenta.getId(),productoVenta)).withRel("actualizar"),
            linkTo(methodOn(ProductoVentaControllerV2.class).deleteProductoVenta(productoVenta.getId())).withRel("eliminar"),
            linkTo(methodOn(ProductoVentaControllerV2.class).partialUpdateProductoVenta(productoVenta.getId(), productoVenta)).withRel("reemplazar una línea")
           
           
        );
    }
}
