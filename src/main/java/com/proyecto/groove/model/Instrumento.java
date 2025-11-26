package com.proyecto.groove.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Instrumento {
    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "producto_id")
    private Producto producto;

   // @Column(name = "nombreInstrumento", length = 80, nullable = false)
    //private String nombreInstrumento;

    @Column(name = "marcaInstrumento", length = 50, nullable = false)
    private String marca;

    @Column(name = "modeloInstrumento", length = 50, nullable = false)
    private String modelo;

    @Column(name = "anoFabricacionInstrumento", length = 4, nullable = true)
    private Integer anoFabricacion;

    @ManyToOne
    @JoinColumn(name="instrumento_tipoid")
    private Tipo tipo;


    
    
}
//como el id ya está en producto, cuando se haga la union se dira que este nombre va en la union