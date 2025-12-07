package com.proyecto.groove.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombreUsuario", length = 50, nullable = false)
    private String nombre;

    @Column(name = "apellidoUsuario", length = 50, nullable = false)
    private String apellido;

    @Column(name = "correoUsuario", length = 80, nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasenaUsuario", length = 100, nullable = false)
    private String contrasena;

   @ManyToMany
    @JoinTable(
        name = "usuario_direccion",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "direccion_id")
    )
    private List<Direccion> direccion;
    

    @ManyToOne
    @JoinColumn(name="usuario_rolid")
    private Rol rol;
}
