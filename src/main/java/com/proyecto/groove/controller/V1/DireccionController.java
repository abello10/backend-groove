package com.proyecto.groove.controller.V1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.proyecto.groove.model.Direccion;
import com.proyecto.groove.service.DireccionService;

@RestController
@RequestMapping("/api/v1/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<Direccion>> getAllDirecciones() {
        List<Direccion> direcciones = direccionService.findAll();
        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(direcciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> getDireccionById(@PathVariable Integer id) {
        Direccion direccion = direccionService.findById(id);
        if (direccion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(direccion);
    }

    @PostMapping
    public ResponseEntity<Direccion> createDireccion(@RequestBody Direccion direccion) {
        Direccion newDireccion = direccionService.save(direccion);
        return ResponseEntity.status(201).body(newDireccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direccion> updateDireccion(@PathVariable Integer id, @RequestBody Direccion direccion) {
        direccion.setId(id);
        Direccion updatedDireccion = direccionService.save(direccion);
        return ResponseEntity.ok(updatedDireccion);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Direccion> partialUpdateDireccion(@PathVariable Integer id, @RequestBody Direccion direccion) {
        direccion.setId(id);
        Direccion updatedDireccion = direccionService.partialUpdate(direccion);

        if (updatedDireccion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDireccion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDireccion(@PathVariable Integer id) {
        if (direccionService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        direccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //relaciones
    @GetMapping("/comunas/{comunaId}")
    public ResponseEntity<List<Direccion>> getDireccionesByComuna(@PathVariable Integer comunaId) {
        List<Direccion> direcciones = direccionService.findByComunaId(comunaId);
        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(direcciones);
    }

    @DeleteMapping("/comunas/{comunaId}")
    public ResponseEntity<Void> deleteDireccionesByComuna(@PathVariable Integer comunaId) {
        direccionService.deleteByComunaId(comunaId);
        return ResponseEntity.noContent().build();
    }
}