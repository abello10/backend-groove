package com.proyecto.groove.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombreProducto", length = 80, nullable = false)
    private String nombre;

    @Column(name = "descripcionProducto", length = 300, nullable = false)
    private String descripcion;

    @Column(name = "precioProducto", nullable = false)
    private Integer precio;

    @Column(name = "stockProducto", nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name="producto_accesorioid")
    private Accesorio accesorio;

    @ManyToOne
    @JoinColumn(name="producto_instrumentoid")
    private Instrumento instrumento;

    @ManyToOne
    @JoinColumn(name="producto_musicaid")
    private Musica musica;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Imagen> imagenes;




    //@PrePersist
    //@PreUpdate
    //private void validarUnSoloTipo(){
      //  int count = 0;
        //if(accesorio != null) count++;
        //if(instrumento != null) count++;
        //if(musica != null) count++;

        //if (count != 1){
          //  throw new IllegalStateException(
            //    "El producto debe ser de un solo tipo"
           // );
        //}
    //}
}
