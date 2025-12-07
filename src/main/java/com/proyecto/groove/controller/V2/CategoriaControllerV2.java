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


import com.proyecto.groove.model.Categoria;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.proyecto.groove.service.CategoriaService;
import com.proyecto.groove.assembler.CategoriaModelAssembler;

@RestController
@RequestMapping("/api/v2/categoria")
public class CategoriaControllerV2 {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las categorias", description = "Muestra todas las categorias de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Categoria>>> getAllCategorias() {
        List<EntityModel<Categoria>> categorias = categoriaService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            categorias,
            linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar categoria", description = "Muestra una categoria por ID")
    public ResponseEntity<EntityModel<Categoria>> getCategoriaById(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(categoria));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear categoria",description = "Permite crear una categoria")
    public ResponseEntity<EntityModel<Categoria>> createCategoria(@RequestBody Categoria categoria) {
        Categoria newCategoria = categoriaService.save(categoria);
        return ResponseEntity
            .created(linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(newCategoria.getId())).toUri())
            .body(assembler.toModel(newCategoria));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar categoria", description = "Permite actualizar una categoria ya creada")
    public ResponseEntity<EntityModel<Categoria>> updateCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        Categoria updatedCategoria = categoriaService.save(categoria);
        return ResponseEntity.ok(assembler.toModel(updatedCategoria));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de una categoria", description = "Permite actualizar una linea de una categoria")
    public ResponseEntity<EntityModel<Categoria>> partialUpdateCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        Categoria updatedCategoria = categoriaService.partialUpdate(categoria);
        
        if (updatedCategoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedCategoria));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar categoria", description = "Permite borrar una categoria por su ID")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}