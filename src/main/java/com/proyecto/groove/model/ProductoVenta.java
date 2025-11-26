package com.proyecto.groove.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductoVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cantidadProductoVenta", length = 10, nullable = false)
    private Integer cantidad;

   @ManyToOne
    @JoinColumn(name = "productoVenta_ventaid")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "productoVenta_productoid")
    private Producto producto;
}
