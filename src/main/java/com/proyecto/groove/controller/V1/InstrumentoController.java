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

import com.proyecto.groove.model.Instrumento;
import com.proyecto.groove.service.InstrumentoService;

@RestController
@RequestMapping("/api/v1/instrumentos")
public class InstrumentoController {

    @Autowired
    private InstrumentoService instrumentoService;

    @GetMapping
    public ResponseEntity<List<Instrumento>> getAllInstrumentos() {
        List<Instrumento> instrumentos = instrumentoService.findAll();
        if (instrumentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(instrumentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrumento> getInstrumentoById(@PathVariable Integer id) {
        Instrumento instrumento = instrumentoService.findById(id);
        if (instrumento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instrumento);
    }

    @PostMapping
    public ResponseEntity<Instrumento> createInstrumento(@RequestBody Instrumento instrumento) {
        Instrumento newInstrumento = instrumentoService.save(instrumento);
        return ResponseEntity.status(201).body(newInstrumento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrumento> updateInstrumento(@PathVariable Integer id, @RequestBody Instrumento instrumento) {
        instrumento.setId(id);
        Instrumento updatedInstrumento = instrumentoService.save(instrumento);
        return ResponseEntity.ok(updatedInstrumento);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Instrumento> partialUpdateInstrumento(@PathVariable Integer id, @RequestBody Instrumento instrumento) {
        instrumento.setId(id);
        Instrumento updatedInstrumento = instrumentoService.partialUpdate(instrumento);

        if (updatedInstrumento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedInstrumento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstrumento(@PathVariable Integer id) {
        if (instrumentoService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        instrumentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //relaciones
    @GetMapping("/tipos/{tipoId}")
    public ResponseEntity<List<Instrumento>> getInstrumentosByTipo(@PathVariable Integer tipoId) {
        List<Instrumento> instrumentos = instrumentoService.findByTipoId(tipoId);
        if (instrumentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(instrumentos);
    }

    @DeleteMapping("/tipos/{tipoId}")
    public ResponseEntity<Void> deleteInstrumentosByTipo(@PathVariable Integer tipoId) {
        instrumentoService.deleteByTipoId(tipoId);
        return ResponseEntity.noContent().build();
    }
}