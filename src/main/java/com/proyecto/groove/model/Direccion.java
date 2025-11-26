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
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "calleDireccion", length = 60, nullable = false)
    private String calle;

    @Column(name = "numeroDireccion", length = 20, nullable = false)
    private Integer numero;

    @Column(name = "deptocasaDireccion", length = 30, nullable = true)
    private String deptocasa;

    @ManyToOne
    @JoinColumn(name="direccion_comunaId")
    private Comuna comuna;

    
}
