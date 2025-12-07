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

import com.proyecto.groove.model.Tipo;
import com.proyecto.groove.service.TipoService;

@RestController
@RequestMapping("/api/v1/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping
    public ResponseEntity<List<Tipo>> getAllTipos() {
        List<Tipo> tipos = tipoService.findAll();
        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> getTipoById(@PathVariable Integer id) {
        Tipo tipo = tipoService.findById(id);
        if (tipo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipo);
    }

    @PostMapping
    public ResponseEntity<Tipo> createTipo(@RequestBody Tipo tipo) {
        Tipo newTipo = tipoService.save(tipo);
        return ResponseEntity.status(201).body(newTipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> updateTipo(@PathVariable Integer id, @RequestBody Tipo tipo) {
        tipo.setId(id);
        Tipo updatedTipo = tipoService.save(tipo);
        return ResponseEntity.ok(updatedTipo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tipo> partialUpdateTipo(@PathVariable Integer id, @RequestBody Tipo tipo) {
        tipo.setId(id);
        Tipo updatedTipo = tipoService.partialUpdate(tipo);

        if (updatedTipo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipo(@PathVariable Integer id) {
        if (tipoService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        tipoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //relaciones
    @GetMapping("/categorias/{categoriaId}")
    public ResponseEntity<List<Tipo>> getTiposByCategoria(@PathVariable Integer categoriaId) {
        List<Tipo> tipos = tipoService.findByCategoriaId(categoriaId);
        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipos);
    }

    @DeleteMapping("/categorias/{categoriaId}")
    public ResponseEntity<Void> deleteTiposByCategoria(@PathVariable Integer categoriaId) {
        tipoService.deleteByCategoriaId(categoriaId);
        return ResponseEntity.noContent().build();
    }
}