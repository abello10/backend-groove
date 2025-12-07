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


import com.proyecto.groove.service.MetodoEnvioService;
import com.proyecto.groove.assembler.MetodoEnvioModelAssembler;
import com.proyecto.groove.model.MetodoEnvio;

@RestController
@RequestMapping("/api/v2/metodos-envio")
public class MetodoEnvioControllerV2 {

    @Autowired
    private MetodoEnvioService metodoEnvioService;

    @Autowired
    private MetodoEnvioModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los metodos de envio", description = "Muestra todos los metodos de envio de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<MetodoEnvio>>> getAllMetodosEnvio() {
        List<EntityModel<MetodoEnvio>> metodosEnvio = metodoEnvioService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (metodosEnvio.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            metodosEnvio,
            linkTo(methodOn(MetodoEnvioControllerV2.class).getAllMetodosEnvio()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar metodo envio", description = "Muestra un metodo de envio por ID")
    public ResponseEntity<EntityModel<MetodoEnvio>> getMetodoEnvioById(@PathVariable Integer id) {
        MetodoEnvio metodoEnvio = metodoEnvioService.findById(id);
        if (metodoEnvio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(metodoEnvio));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear metodo de envio",description = "Permite crear un metodo de envio")
    public ResponseEntity<EntityModel<MetodoEnvio>> createMetodoEnvio(@RequestBody MetodoEnvio metodoEnvio) {
        MetodoEnvio newMetodoEnvio = metodoEnvioService.save(metodoEnvio);
        return ResponseEntity
            .created(linkTo(methodOn(MetodoEnvioControllerV2.class).getMetodoEnvioById(newMetodoEnvio.getId())).toUri())
            .body(assembler.toModel(newMetodoEnvio));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar metodo de envio", description = "Permite actualizar un metodo de envio ya creado")
    public ResponseEntity<EntityModel<MetodoEnvio>> updateMetodoEnvio(@PathVariable Integer id, @RequestBody MetodoEnvio metodoEnvio) {
        metodoEnvio.setId(id);
        MetodoEnvio updatedMetodoEnvio = metodoEnvioService.save(metodoEnvio);
        return ResponseEntity.ok(assembler.toModel(updatedMetodoEnvio));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un metodo de envio", description = "Permite actualizar una linea de un metodo de envio")
    public ResponseEntity<EntityModel<MetodoEnvio>> partialUpdateMetodoEnvio(@PathVariable Integer id, @RequestBody MetodoEnvio metodoEnvio) {
        MetodoEnvio updatedMetodoEnvio = metodoEnvioService.partialUpdate(metodoEnvio);
        
        if (updatedMetodoEnvio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedMetodoEnvio));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar metodo de envio", description = "Permite borrar un metodo de envio por su ID")
    public ResponseEntity<Void> deleteMetodoEnvio(@PathVariable Integer id) {
        MetodoEnvio metodoEnvio = metodoEnvioService.findById(id);
        if (metodoEnvio == null) {
            return ResponseEntity.notFound().build();
        }
        
        metodoEnvioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}