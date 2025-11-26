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


import com.proyecto.groove.service.MusicaService;
import com.proyecto.groove.assembler.MusicaModelAssembler;
import com.proyecto.groove.model.Musica;

@RestController
@RequestMapping("/api/v2/musica")
public class MusicaControllerV2 {

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private MusicaModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener toda la musica", description = "Muestra toda la musica de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Musica>>> getAllMusica() {
        List<EntityModel<Musica>> musica = musicaService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (musica.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            musica,
            linkTo(methodOn(MusicaControllerV2.class).getAllMusica()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar musica", description = "Muestra musica por ID")
    public ResponseEntity<EntityModel<Musica>> getMusicaById(@PathVariable Integer id) {
        Musica musica = musicaService.findById(id);
        if (musica == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(musica));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear musica",description = "Permite crear musica")
    public ResponseEntity<EntityModel<Musica>> createMusica(@RequestBody Musica musica) {
        Musica newMusica = musicaService.save(musica);
        return ResponseEntity
            .created(linkTo(methodOn(MusicaControllerV2.class).getMusicaById(newMusica.getProductoId())).toUri())
            .body(assembler.toModel(newMusica));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar musica", description = "Permite actualizar musica ya creada")
    public ResponseEntity<EntityModel<Musica>> updateMusica(@PathVariable Integer id, @RequestBody Musica musica) {
        musica.setProductoId(id);
        Musica updatedMusica = musicaService.save(musica);
        return ResponseEntity.ok(assembler.toModel(updatedMusica));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de musica", description = "Permite actualizar una linea de musica")
    public ResponseEntity<EntityModel<Musica>> partialUpdateMusica(@PathVariable Integer id, @RequestBody Musica musica) {
        Musica updatedMusica = musicaService.partialUpdate(musica);
        
        if (updatedMusica== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedMusica));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar musica", description = "Permite borrar musica por su ID")
    public ResponseEntity<Void> deleteMusica(@PathVariable Integer id) {
        Musica musica = musicaService.findById(id);
        if (musica == null) {
            return ResponseEntity.notFound().build();
        }
        
        musicaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
