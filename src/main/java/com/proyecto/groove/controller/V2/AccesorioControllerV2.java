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

import com.proyecto.groove.assembler.AccesorioModelAssembler;
import com.proyecto.groove.model.Accesorio;
import com.proyecto.groove.service.AccesorioService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping("/api/v2/accesorios")
public class AccesorioControllerV2 {

    @Autowired
    private AccesorioService accesorioService;

    @Autowired
    private AccesorioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los accesorios", description = "Muestra todos los accesorios de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Accesorio>>> getAllAccesorios() {
        List<EntityModel<Accesorio>> accesorios = accesorioService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (accesorios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            accesorios,
            linkTo(methodOn(AccesorioControllerV2.class).getAllAccesorios()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar accesorio", description = "Muestra un accesorio por ID")
    public ResponseEntity<EntityModel<Accesorio>> getAccesorioById(@PathVariable Integer id) {
        Accesorio accesorio = accesorioService.findById(id);
        if (accesorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(accesorio));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear accesorio",description = "Permite crear un accesorio")
    public ResponseEntity<EntityModel<Accesorio>> createAccesorio(@RequestBody Accesorio accesorio) {
        Accesorio newAccesorio = accesorioService.save(accesorio);
        return ResponseEntity
            .created(linkTo(methodOn(AccesorioControllerV2.class).getAccesorioById(newAccesorio.getId())).toUri())
            .body(assembler.toModel(newAccesorio));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar accesorio", description = "Permite actualizar un accesorio ya creado")
    public ResponseEntity<EntityModel<Accesorio>> updateAccesorio(@PathVariable Integer id, @RequestBody Accesorio accesorio) {
        accesorio.setId(id);
        Accesorio updatedAccesorio = accesorioService.save(accesorio);
        return ResponseEntity.ok(assembler.toModel(updatedAccesorio));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un accesorio", description = "Permite actualizar una linea de un accesorio")
    public ResponseEntity<EntityModel<Accesorio>> partialUpdateAccesorio(@PathVariable Integer id, @RequestBody Accesorio accesorio) {
        Accesorio updatedAccesorio = accesorioService.partialUpdate(accesorio);
        
        if (updatedAccesorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedAccesorio));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar accesorio", description = "Permite borrar un accesorio por su ID")
    public ResponseEntity<Void> deleteAccesorio(@PathVariable Integer id) {
        Accesorio accesorio = accesorioService.findById(id);
        if (accesorio == null) {
            return ResponseEntity.notFound().build();
        }
        
        accesorioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}