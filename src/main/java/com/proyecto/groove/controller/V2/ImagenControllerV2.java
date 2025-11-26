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


import com.proyecto.groove.service.ImagenService;
import com.proyecto.groove.assembler.ImagenModelAssembler;
import com.proyecto.groove.model.Imagen;

@RestController
@RequestMapping("/api/v2/imagen")
public class ImagenControllerV2 {

    @Autowired
    private ImagenService imagenService;

    @Autowired
    private ImagenModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las imagenes", description = "Muestra todas las imagenes de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Imagen>>> getAllImagenes() {
        List<EntityModel<Imagen>> imagenes = imagenService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (imagenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            imagenes,
            linkTo(methodOn(ImagenControllerV2.class).getAllImagenes()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar imagen", description = "Muestra una imagen por ID")
    public ResponseEntity<EntityModel<Imagen>> getImagenById(@PathVariable Integer id) {
        Imagen imagen = imagenService.findById(id);
        if (imagen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(imagen));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear imagen",description = "Permite crear una imagen")
    public ResponseEntity<EntityModel<Imagen>> createImagen(@RequestBody Imagen imagen) {
        Imagen newImagen = imagenService.save(imagen);
        return ResponseEntity
            .created(linkTo(methodOn(ImagenControllerV2.class).getImagenById(newImagen.getId())).toUri())
            .body(assembler.toModel(newImagen));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar imagen", description = "Permite actualizar una imagen ya creada")
    public ResponseEntity<EntityModel<Imagen>> updateImagen(@PathVariable Integer id, @RequestBody Imagen imagen) {
        imagen.setId(id);
        Imagen updatedImagen = imagenService.save(imagen);
        return ResponseEntity.ok(assembler.toModel(updatedImagen));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de una imagen", description = "Permite actualizar una linea de una imagen")
    public ResponseEntity<EntityModel<Imagen>> partialUpdateImagen(@PathVariable Integer id, @RequestBody Imagen imagen) {
        Imagen updatedImagen = imagenService.partialUpdate(imagen);
        
        if (updatedImagen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedImagen));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar imagen", description = "Permite borrar una imagen por su ID")
    public ResponseEntity<Void> deleteImagen(@PathVariable Integer id) {
        Imagen imagen = imagenService.findById(id);
        if (imagen == null) {
            return ResponseEntity.notFound().build();
        }
        
        imagenService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}