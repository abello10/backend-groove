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


import com.proyecto.groove.service.TipoService;
import com.proyecto.groove.assembler.TipoModelAssembler;
import com.proyecto.groove.model.Tipo;

@RestController
@RequestMapping("/api/v2/tipo")
public class TipoControllerV2 {

    @Autowired
    private TipoService tipoService;

    @Autowired
    private TipoModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los tipos", description = "Muestra todos los tipos de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Tipo>>> getAllTipos() {
        List<EntityModel<Tipo>> tipo = tipoService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (tipo.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            tipo,
            linkTo(methodOn(TipoControllerV2.class).getAllTipos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar un tipo", description = "Muestra un tipo por ID")
    public ResponseEntity<EntityModel<Tipo>> getTipoById(@PathVariable Integer id) {
        Tipo tipo = tipoService.findById(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(tipo));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un tipo",description = "Permite crear un tipo")
    public ResponseEntity<EntityModel<Tipo>> createTipo(@RequestBody Tipo tipo) {
        Tipo newTipo = tipoService.save(tipo);
        return ResponseEntity
            .created(linkTo(methodOn(TipoControllerV2.class).getTipoById(newTipo.getId())).toUri())
            .body(assembler.toModel(newTipo));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un tipo", description = "Permite actualizar un tipo ya creado")
    public ResponseEntity<EntityModel<Tipo>> updateTipo(@PathVariable Integer id, @RequestBody Tipo tipo) {
        tipo.setId(id);
        Tipo updatedTipo = tipoService.save(tipo);
        return ResponseEntity.ok(assembler.toModel(updatedTipo));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un tipo", description = "Permite actualizar una linea de un tipo")
    public ResponseEntity<EntityModel<Tipo>> partialUpdateTipo(@PathVariable Integer id, @RequestBody Tipo tipo) {
        Tipo updatedTipo = tipoService.partialUpdate(tipo);
        
        if (updatedTipo== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedTipo));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar un tipo", description = "Permite borrar un tipo por su ID")
    public ResponseEntity<Void> deleteTipo(@PathVariable Integer id) {
        Tipo tipo = tipoService.findById(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        
        tipoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}