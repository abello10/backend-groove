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
public class Accesorio {
    @Id
    private Integer productoId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "producto_id")
    private Producto producto;

    //@Column(name = "nombreAccesorio", length = 80, nullable = false)
    //private String nombreAccesorio;

    @Column(name = "marcaAccesorio", length = 50, nullable = false)
    private String marca;

    @Column(name = "modeloAccesorio", length = 50, nullable = false)
    private String modelo;

}
//como el id ya está en producto, cuando se haga la union se dira que este nombre va en la union