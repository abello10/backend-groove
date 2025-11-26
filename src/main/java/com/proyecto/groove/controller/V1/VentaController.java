package com.proyecto.groove.controller.V1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.groove.model.Venta;
import com.proyecto.groove.service.VentaService;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.findAll();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Integer id) {
        Venta venta = ventaService.findById(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venta);
    }

    @PostMapping
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) {
        Venta newVenta = ventaService.save(venta);
        return ResponseEntity.status(201).body(newVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        venta.setId(id);
        Venta updatedVenta = ventaService.save(venta);
        return ResponseEntity.ok(updatedVenta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Venta> partialUpdateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        venta.setId(id);
        Venta updatedVenta = ventaService.partialUpdate(venta);

        if (updatedVenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Integer id) {
        if (ventaService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //relaciones (SON UN MONTÓN XD)
    @GetMapping("/metodos-envio/{metodoEnvioId}")
    public ResponseEntity<List<Venta>> getVentasByMetodoEnvio(@PathVariable Integer metodoEnvioId) {
        List<Venta> ventas = ventaService.findByMetodoEnvioId(metodoEnvioId);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @DeleteMapping("/metodos-envio/{metodoEnvioId}")
    public ResponseEntity<Void> deleteVentasByMetodoEnvio(@PathVariable Integer metodoEnvioId) {
        ventaService.deleteByMetodoEnvioId(metodoEnvioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metodos-pago/{metodoPagoId}")
    public ResponseEntity<List<Venta>> getVentasByMetodoPago(@PathVariable Integer metodoPagoId) {
        List<Venta> ventas = ventaService.findByMetodoPagoId(metodoPagoId);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @DeleteMapping("/metodos-pago/{metodoPagoId}")
    public ResponseEntity<Void> deleteVentasByMetodoPago(@PathVariable Integer metodoPagoId) {
        ventaService.deleteByMetodoPagoId(metodoPagoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<Venta>> getVentasByUsuario(@PathVariable Integer usuarioId) {
        List<Venta> ventas = ventaService.findByUsuarioId(usuarioId);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @DeleteMapping("/usuarios/{usuarioId}")
    public ResponseEntity<Void> deleteVentasByUsuario(@PathVariable Integer usuarioId) {
        ventaService.deleteByUsuarioId(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
