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


import com.proyecto.groove.service.DireccionService;
import com.proyecto.groove.assembler.DireccionModelAssembler;
import com.proyecto.groove.model.Direccion;

@RestController
@RequestMapping("/api/v2/direccion")
public class DireccionControllerV2 {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private DireccionModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las direcciones", description = "Muestra todas las direcciones de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Direccion>>> getAllDirecciones() {
        List<EntityModel<Direccion>> direcciones = direccionService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            direcciones,
            linkTo(methodOn(DireccionControllerV2.class).getAllDirecciones()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar direccion", description = "Muestra una direccion por ID")
    public ResponseEntity<EntityModel<Direccion>> getDireccionById(@PathVariable Integer id) {
        Direccion direccion = direccionService.findById(id);
        if (direccion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(direccion));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear direccion",description = "Permite crear una direccion")
    public ResponseEntity<EntityModel<Direccion>> createDireccion(@RequestBody Direccion direccion) {
        Direccion newDireccion = direccionService.save(direccion);
        return ResponseEntity
            .created(linkTo(methodOn(DireccionControllerV2.class).getDireccionById(newDireccion.getId())).toUri())
            .body(assembler.toModel(newDireccion));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar direccion", description = "Permite actualizar una direccion ya creada")
    public ResponseEntity<EntityModel<Direccion>> updateDireccion(@PathVariable Integer id, @RequestBody Direccion direccion) {
        direccion.setId(id);
        Direccion updatedDireccion = direccionService.save(direccion);
        return ResponseEntity.ok(assembler.toModel(updatedDireccion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de una direccion", description = "Permite actualizar una linea de una direccion")
    public ResponseEntity<EntityModel<Direccion>> partialUpdateDireccion(@PathVariable Integer id, @RequestBody Direccion direccion) {
        Direccion updatedDireccion = direccionService.partialUpdate(direccion);
        
        if (updatedDireccion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedDireccion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar direccion", description = "Permite borrar una direccion por su ID")
    public ResponseEntity<Void> deleteDireccion(@PathVariable Integer id) {
        Direccion direccion = direccionService.findById(id);
        if (direccion == null) {
            return ResponseEntity.notFound().build();
        }
        
        direccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}