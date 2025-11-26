package com.proyecto.groove.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.groove.model.Musica;
import com.proyecto.groove.repository.MusicaRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
@SuppressWarnings("null")
public class MusicaService {
     @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private ProductoService productoService;

    public List<Musica> findAll(){
        return musicaRepository.findAll();
    }

    public Musica findById(Integer id){
        Musica musica = musicaRepository.findById(id).orElse(null);
        return musica;
    }

    public Musica save(Musica musica){
        return musicaRepository.save(musica);
    }

    public Musica partialUpdate(Musica musica){
        Musica existingMusica = musicaRepository.findById(musica.getId()).orElse(null);
        if (existingMusica != null){
            if (musica.getAnoLanzamiento() != null){
                existingMusica.setAnoLanzamiento(musica.getAnoLanzamiento());
            }
            if (musica.getGenero() != null){
                existingMusica.setGenero(musica.getGenero());
            }
            if (musica.getAlbum() != null){
                existingMusica.setAlbum(musica.getAlbum());
            }
            if (musica.getArtista() != null){
                existingMusica.setArtista(musica.getArtista());
            }


            return musicaRepository.save(existingMusica);
        }
        return null;
    }

    public void deleteById(Integer id){
        productoService.deleteByAccesorioId(id);
        musicaRepository.deleteById(id);
    }

    
}
