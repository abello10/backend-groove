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


import com.proyecto.groove.service.RolService;
import com.proyecto.groove.assembler.RolModelAssembler;
import com.proyecto.groove.model.Rol;

@RestController
@RequestMapping("/api/v2/rol")
public class RolControllerV2 {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los roles", description = "Muestra todoss los roles de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Rol>>> getAllRoles() {
        List<EntityModel<Rol>> rol = rolService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (rol.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            rol,
            linkTo(methodOn(RolControllerV2.class).getAllRoles()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar un rol", description = "Muestra un rol por ID")
    public ResponseEntity<EntityModel<Rol>> getRolById(@PathVariable Integer id) {
        Rol rol = rolService.findById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(rol));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un rol",description = "Permite crear un rol")
    public ResponseEntity<EntityModel<Rol>> createRol(@RequestBody Rol rol) {
        Rol newRol = rolService.save(rol);
        return ResponseEntity
            .created(linkTo(methodOn(RolControllerV2.class).getRolById(newRol.getId())).toUri())
            .body(assembler.toModel(newRol));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un rol", description = "Permite actualizar un rol ya creado")
    public ResponseEntity<EntityModel<Rol>> updateRol(@PathVariable Integer id, @RequestBody Rol rol) {
        rol.setId(id);
        Rol updatedRol = rolService.save(rol);
        return ResponseEntity.ok(assembler.toModel(updatedRol));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un rol", description = "Permite actualizar una linea de un rol")
    public ResponseEntity<EntityModel<Rol>> partialUpdateRol(@PathVariable Integer id, @RequestBody Rol rol) {
        Rol updatedRol = rolService.partialUpdate(rol);
        
        if (updatedRol== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedRol));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar un rol", description = "Permite borrar un rol por su ID")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        Rol rol = rolService.findById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        
        rolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}