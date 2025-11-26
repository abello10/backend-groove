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

import com.proyecto.groove.model.Musica;
import com.proyecto.groove.service.MusicaService;

@RestController
@RequestMapping("/api/v1/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;

    @GetMapping
    public ResponseEntity<List<Musica>> getAllMusica() {
        List<Musica> musicas = musicaService.findAll();
        if (musicas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(musicas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musica> getMusicaById(@PathVariable Integer id) {
        Musica musica = musicaService.findById(id);
        if (musica == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(musica);
    }

    @PostMapping
    public ResponseEntity<Musica> createMusica(@RequestBody Musica musica) {
        Musica newMusica = musicaService.save(musica);
        return ResponseEntity.status(201).body(newMusica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musica> updateMusica(@PathVariable Integer id, @RequestBody Musica musica) {
        musica.setId(id);
        Musica updatedMusica = musicaService.save(musica);
        return ResponseEntity.ok(updatedMusica);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Musica> partialUpdateMusica(@PathVariable Integer id, @RequestBody Musica musica) {
        musica.setId(id);
        Musica updatedMusica = musicaService.partialUpdate(musica);

        if (updatedMusica == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMusica);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusica(@PathVariable Integer id) {
        if (musicaService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        musicaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}