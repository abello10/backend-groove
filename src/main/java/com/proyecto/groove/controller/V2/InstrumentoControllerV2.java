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


import com.proyecto.groove.service.InstrumentoService;
import com.proyecto.groove.assembler.InstrumentoModelAssembler;
import com.proyecto.groove.model.Instrumento;

@RestController
@RequestMapping("/api/v2/instrumento")
public class InstrumentoControllerV2 {

    @Autowired
    private InstrumentoService instrumentoService;

    @Autowired
    private InstrumentoModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los instrumentos", description = "Muestra todos los instrumentos de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Instrumento>>> getAllInstrumentos() {
        List<EntityModel<Instrumento>> instrumento = instrumentoService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (instrumento.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            instrumento,
            linkTo(methodOn(InstrumentoControllerV2.class).getAllInstrumentos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar instrumento", description = "Muestra un instrumento por ID")
    public ResponseEntity<EntityModel<Instrumento>> getInstrumentoById(@PathVariable Integer id) {
        Instrumento instrumento = instrumentoService.findById(id);
        if (instrumento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(instrumento));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear instrumento",description = "Permite crear un instrumento")
    public ResponseEntity<EntityModel<Instrumento>> createInstrumento(@RequestBody Instrumento instrumento) {
        Instrumento newInstrumento = instrumentoService.save(instrumento);
        return ResponseEntity
            .created(linkTo(methodOn(InstrumentoControllerV2.class).getInstrumentoById(newInstrumento.getProductoId())).toUri())
            .body(assembler.toModel(newInstrumento));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar instrumento", description = "Permite actualizar un instrumento ya creado")
    public ResponseEntity<EntityModel<Instrumento>> updateInstrumento(@PathVariable Integer id, @RequestBody Instrumento instrumento) {
        instrumento.setProductoId(id);
        Instrumento updatedInstrumento = instrumentoService.save(instrumento);
        return ResponseEntity.ok(assembler.toModel(updatedInstrumento));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un instrumento", description = "Permite actualizar una linea de un instrumento")
    public ResponseEntity<EntityModel<Instrumento>> partialUpdateInstrumento(@PathVariable Integer id, @RequestBody Instrumento instrumento) {
        Instrumento updatedInstrumento = instrumentoService.partialUpdate(instrumento);
        
        if (updatedInstrumento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedInstrumento));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar instrumento", description = "Permite borrar un instrumento por su ID")
    public ResponseEntity<Void> deleteInstrumento(@PathVariable Integer id) {
        Instrumento instrumento = instrumentoService.findById(id);
        if (instrumento == null) {
            return ResponseEntity.notFound().build();
        }
        
        instrumentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}