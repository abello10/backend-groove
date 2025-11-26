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

import com.proyecto.groove.model.Imagen;
import com.proyecto.groove.service.ImagenService;

@RestController
@RequestMapping("/api/v1/imagenes")
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @GetMapping
    public ResponseEntity<List<Imagen>> getAllImagenes() {
        List<Imagen> imagenes = imagenService.findAll();
        if (imagenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(imagenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagen> getImagenById(@PathVariable Integer id) {
        Imagen imagen = imagenService.findById(id);
        if (imagen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imagen);
    }

    @PostMapping
    public ResponseEntity<Imagen> createImagen(@RequestBody Imagen imagen) {
        Imagen newImagen = imagenService.save(imagen);
        return ResponseEntity.status(201).body(newImagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imagen> updateImagen(@PathVariable Integer id, @RequestBody Imagen imagen) {
        imagen.setId(id);
        Imagen updatedImagen = imagenService.save(imagen);
        return ResponseEntity.ok(updatedImagen);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Imagen> partialUpdateImagen(@PathVariable Integer id, @RequestBody Imagen imagen) {
        imagen.setId(id);
        Imagen updatedImagen = imagenService.partialUpdate(imagen);

        if (updatedImagen == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedImagen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Integer id) {
        if (imagenService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        imagenService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //relaciones
    @GetMapping("/productos/{productoId}")
    public ResponseEntity<List<Imagen>> getImagenesByProducto(@PathVariable Integer productoId) {
        List<Imagen> imagenes = imagenService.findByProductoId(productoId);
        if (imagenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(imagenes);
    }

    @DeleteMapping("/productos/{productoId}")
    public ResponseEntity<Void> deleteImagenesByProducto(@PathVariable Integer productoId) {
        imagenService.deleteByProductoId(productoId);
        return ResponseEntity.noContent().build();
    }
}