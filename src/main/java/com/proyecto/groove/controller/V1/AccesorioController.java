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

import com.proyecto.groove.model.Accesorio;
import com.proyecto.groove.service.AccesorioService;

@RestController
@RequestMapping("/api/v1/accesorios")
public class AccesorioController {

    @Autowired
    private AccesorioService accesorioService;


    @GetMapping
    public ResponseEntity<List<Accesorio>> getAllAccesorios() {
        List<Accesorio> accesorios = accesorioService.findAll();
        if (accesorios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accesorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accesorio> getAccesorioById(@PathVariable Integer id) {
        Accesorio accesorio = accesorioService.findById(id);
        if (accesorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accesorio);
    }

    @PostMapping
    public ResponseEntity<Accesorio> createAccesorio(@RequestBody Accesorio accesorio) {
        Accesorio newAccesorio = accesorioService.save(accesorio);
        return ResponseEntity.status(201).body(newAccesorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accesorio> updateAccesorio(@PathVariable Integer id, @RequestBody Accesorio accesorio) {
        accesorio.setProductoId(id);
        Accesorio updatedAccesorio = accesorioService.save(accesorio);
        return ResponseEntity.ok(updatedAccesorio);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Accesorio> partialUpdateAccesorio(@PathVariable Integer id, @RequestBody Accesorio accesorio) {
        accesorio.setProductoId(id);
        
        Accesorio updatedAccesorio = accesorioService.partialUpdate(accesorio);
        
        if (updatedAccesorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAccesorio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccesorio(@PathVariable Integer id) {
        if (accesorioService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        accesorioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}