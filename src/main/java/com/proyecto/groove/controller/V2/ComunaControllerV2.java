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


import com.proyecto.groove.service.ComunaService;
import com.proyecto.groove.assembler.ComunaModelAssembler;
import com.proyecto.groove.model.Comuna;

@RestController
@RequestMapping("/api/v2/comuna")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las comunas", description = "Muestra todas las comunas de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Comuna>>> getAllComunas() {
        List<EntityModel<Comuna>> comunas = comunaService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            comunas,
            linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar comuna", description = "Muestra una comuna por ID")
    public ResponseEntity<EntityModel<Comuna>> getComunaById(@PathVariable Integer id) {
        Comuna comuna = comunaService.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(comuna));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear comuna",description = "Permite crear una comuna")
    public ResponseEntity<EntityModel<Comuna>> createComuna(@RequestBody Comuna comuna) {
        Comuna newComuna = comunaService.save(comuna);
        return ResponseEntity
            .created(linkTo(methodOn(ComunaControllerV2.class).getComunaById(newComuna.getId())).toUri())
            .body(assembler.toModel(newComuna));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar comuna", description = "Permite actualizar una comuna ya creada")
    public ResponseEntity<EntityModel<Comuna>> updateComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        comuna.setId(id);
        Comuna updatedComuna = comunaService.save(comuna);
        return ResponseEntity.ok(assembler.toModel(updatedComuna));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de una comuna", description = "Permite actualizar una linea de una comuna")
    public ResponseEntity<EntityModel<Comuna>> partialUpdateComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        Comuna updatedComuna = comunaService.partialUpdate(comuna);
        
        if (updatedComuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedComuna));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar comuna", description = "Permite borrar una comuna por su ID")
    public ResponseEntity<Void> deleteComuna(@PathVariable Integer id) {
        Comuna comuna = comunaService.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        
        comunaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}