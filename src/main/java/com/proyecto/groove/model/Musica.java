package com.proyecto.groove.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Musica {
    @Id
    private Integer Id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "producto_id")
    private Producto producto;

    //@Column(name = "nombreMusica", length = 80, nullable = false)
    //private String nombreMusica;

    @Column(name = "artistaMusica", length = 80, nullable = false)
    private String artista;

    @Column(name = "albumMusica", length = 80, nullable = false)
    private String album;

    @Column(name = "generoMusica", length = 50, nullable = false)
    private String genero;

    @Column(name = "anoLanzamientoMusica", length = 4, nullable = true)
    private Integer anoLanzamiento;


    
    
}
//como el id ya está en producto, cuando se haga la union se dira que este nombre va en la union