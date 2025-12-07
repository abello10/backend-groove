package com.proyecto.groove.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;

    @Column(name = "estadoVenta", length = 20, nullable = false)
    private String estado;

    @Column(name="total",nullable=false)
    private Integer total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<ProductoVenta> productos;

    @ManyToOne
    @JoinColumn(name="venta_metodoPagoid")
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name="venta_metodoEnvioid")
    private MetodoEnvio metodoEnvio;

    @ManyToOne
    @JoinColumn(name="ventaUsuarioid")
    private Usuario usuario;

    //despues de unir tablas ver que se le pone mas
}
