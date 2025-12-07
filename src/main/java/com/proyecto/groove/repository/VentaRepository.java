package com.proyecto.groove.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.groove.model.Venta;


import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Integer>{

    List<Venta> findByMetodoEnvioId(Integer metodoEnvioId);

    List<Venta> findByMetodoPagoId(Integer metodoPagoId);

    List<Venta> findByUsuarioId(Integer usuarioId);
}
