package com.proyecto.groove.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.proyecto.groove.service.ProductoVentaService;
import com.proyecto.groove.assembler.ProductoVentaModelAssembler;
import com.proyecto.groove.model.ProductoVenta;

@RestController
@RequestMapping("/api/v2/productos-ventas")
public class ProductoVentaControllerV2 {

    @Autowired
    private ProductoVentaService productoVentaService;

    @Autowired
    private ProductoVentaModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las ventas de los productos", description = "Muestra todas las ventas de los productos de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<ProductoVenta>>> getAllProductoVenta() {
        List<EntityModel<ProductoVenta>> productoVenta = productoVentaService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (productoVenta.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            productoVenta,
            linkTo(methodOn(ProductoVentaControllerV2.class).getAllProductoVenta()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar una venta de un producto", description = "Muestra una venta de un producto por ID")
    public ResponseEntity<EntityModel<ProductoVenta>> getProductoVentaById(@PathVariable Integer id) {
        ProductoVenta productoVenta = productoVentaService.findById(id);
        if (productoVenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(productoVenta));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear una venta de un producto",description = "Permite crear una venta de un producto")
    public ResponseEntity<EntityModel<ProductoVenta>> createProductoVenta(@RequestBody ProductoVenta productoVenta) {
        ProductoVenta newProductoVenta = productoVentaService.save(productoVenta);
        return ResponseEntity
            .created(linkTo(methodOn(ProductoVentaControllerV2.class).getProductoVentaById(newProductoVenta.getId())).toUri())
            .body(assembler.toModel(newProductoVenta));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar una venta de un producto", description = "Permite actualizar una venta de un producto ya creada")
    public ResponseEntity<EntityModel<ProductoVenta>> updateProductoVenta(@PathVariable Integer id, @RequestBody ProductoVenta productoVenta) {
        productoVenta.setId(id);
        ProductoVenta updatedProductoVenta = productoVentaService.save(productoVenta);
        return ResponseEntity.ok(assembler.toModel(updatedProductoVenta));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de una venta de un producto", description = "Permite actualizar una linea de una venta de un producto")
    public ResponseEntity<EntityModel<ProductoVenta>> partialUpdateProductoVenta(@PathVariable Integer id, @RequestBody ProductoVenta productoVenta) {
        ProductoVenta updatedProductoVenta = productoVentaService.partialUpdate(productoVenta);
        
        if (updatedProductoVenta== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedProductoVenta));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar una venta de un producto", description = "Permite borrar una venta de un producto por su ID")
    public ResponseEntity<Void> deleteProductoVenta(@PathVariable Integer id) {
        ProductoVenta productoVenta = productoVentaService.findById(id);
        if (productoVenta == null) {
            return ResponseEntity.notFound().build();
        }
        
        productoVentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
