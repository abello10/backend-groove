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


import com.proyecto.groove.service.RegionService;
import com.proyecto.groove.assembler.RegionModelAssembler;
import com.proyecto.groove.model.Region;

@RestController
@RequestMapping("/api/v2/region")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las regiones", description = "Muestra todas las regiones de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Region>>> getAllRegion() {
        List<EntityModel<Region>> region = regionService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (region.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            region,
            linkTo(methodOn(RegionControllerV2.class).getAllRegion()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar una region", description = "Muestra una region por ID")
    public ResponseEntity<EntityModel<Region>> getRegionById(@PathVariable Integer id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(region));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear una region",description = "Permite crear una region")
    public ResponseEntity<EntityModel<Region>> createRegion(@RequestBody Region region) {
        Region newRegion = regionService.save(region);
        return ResponseEntity
            .created(linkTo(methodOn(RegionControllerV2.class).getRegionById(newRegion.getId())).toUri())
            .body(assembler.toModel(newRegion));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar una region", description = "Permite actualizar una region ya creada")
    public ResponseEntity<EntityModel<Region>> updateRegion(@PathVariable Integer id, @RequestBody Region region) {
        region.setId(id);
        Region updatedRegion = regionService.save(region);
        return ResponseEntity.ok(assembler.toModel(updatedRegion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de una region", description = "Permite actualizar una linea de una region")
    public ResponseEntity<EntityModel<Region>> partialUpdateRegion(@PathVariable Integer id, @RequestBody Region region) {
        Region updatedRegion = regionService.partialUpdate(region);
        
        if (updatedRegion== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedRegion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar una region", description = "Permite borrar una region por su ID")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        
        regionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}