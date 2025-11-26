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


import com.proyecto.groove.service.MetodoPagoService;
import com.proyecto.groove.assembler.MetodoPagoModelAssembler;
import com.proyecto.groove.model.MetodoPago;

@RestController
@RequestMapping("/api/v2/metodos-pago")
public class MetodoPagoControllerV2 {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private MetodoPagoModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los metodos de pago", description = "Muestra todos los metodos de pago de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<MetodoPago>>> getAllMetodosPago() {
        List<EntityModel<MetodoPago>> metodosPago = metodoPagoService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (metodosPago.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            metodosPago,
            linkTo(methodOn(MetodoPagoControllerV2.class).getAllMetodosPago()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar metodo pago", description = "Muestra un metodo de pago por ID")
    public ResponseEntity<EntityModel<MetodoPago>> getMetodoPagoById(@PathVariable Integer id) {
        MetodoPago metodoPago = metodoPagoService.findById(id);
        if (metodoPago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(metodoPago));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear metodo de pago",description = "Permite crear un metodo de pago")
    public ResponseEntity<EntityModel<MetodoPago>> createMetodoPago(@RequestBody MetodoPago metodoPago) {
        MetodoPago newMetodoPago = metodoPagoService.save(metodoPago);
        return ResponseEntity
            .created(linkTo(methodOn(MetodoPagoControllerV2.class).getMetodoPagoById(newMetodoPago.getId())).toUri())
            .body(assembler.toModel(newMetodoPago));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar metodo de pago", description = "Permite actualizar un metodo de pago ya creado")
    public ResponseEntity<EntityModel<MetodoPago>> updateMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        metodoPago.setId(id);
        MetodoPago updatedMetodoPago = metodoPagoService.save(metodoPago);
        return ResponseEntity.ok(assembler.toModel(updatedMetodoPago));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un metodo de pago", description = "Permite actualizar una linea de un metodo de pago")
    public ResponseEntity<EntityModel<MetodoPago>> partialUpdateMetodoPago(@PathVariable Integer id, @RequestBody MetodoPago metodoPago) {
        MetodoPago updatedMetodoPago = metodoPagoService.partialUpdate(metodoPago);
        
        if (updatedMetodoPago== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedMetodoPago));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar metodo de pago", description = "Permite borrar un metodo de pago por su ID")
    public ResponseEntity<Void> deleteMetodoPago(@PathVariable Integer id) {
        MetodoPago metodoPago = metodoPagoService.findById(id);
        if (metodoPago == null) {
            return ResponseEntity.notFound().build();
        }
        
        metodoPagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}