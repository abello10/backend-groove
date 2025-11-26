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


import com.proyecto.groove.service.VentaService;
import com.proyecto.groove.assembler.VentaModelAssembler;
import com.proyecto.groove.model.Venta;

@RestController
@RequestMapping("/api/v2/venta")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las ventas", description = "Muestra todas las ventas de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> getAllVentas() {
        List<EntityModel<Venta>> venta = ventaService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (venta.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            venta,
            linkTo(methodOn(VentaControllerV2.class).getAllVentas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar una venta", description = "Muestra una venta por ID")
    public ResponseEntity<EntityModel<Venta>> getVentaById(@PathVariable Integer id) {
        Venta venta = ventaService.findById(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(venta));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear una venta",description = "Permite crear una venta")
    public ResponseEntity<EntityModel<Venta>> createVenta(@RequestBody Venta venta) {
        Venta newVenta = ventaService.save(venta);
        return ResponseEntity
            .created(linkTo(methodOn(VentaControllerV2.class).getVentaById(newVenta.getId())).toUri())
            .body(assembler.toModel(newVenta));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar una venta", description = "Permite actualizar una venta ya creada")
    public ResponseEntity<EntityModel<Venta>> updateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        venta.setId(id);
        Venta updatedVenta = ventaService.save(venta);
        return ResponseEntity.ok(assembler.toModel(updatedVenta));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de una venta", description = "Permite actualizar una linea de una venta")
    public ResponseEntity<EntityModel<Venta>> partialUpdateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        Venta updatedVenta = ventaService.partialUpdate(venta);
        
        if (updatedVenta== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedVenta));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar una venta", description = "Permite borrar una venta por su ID")
    public ResponseEntity<Void> deleteVenta(@PathVariable Integer id) {
        Venta venta = ventaService.findById(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}