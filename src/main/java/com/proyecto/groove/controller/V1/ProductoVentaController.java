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

import com.proyecto.groove.model.ProductoVenta;
import com.proyecto.groove.service.ProductoVentaService;

@RestController
@RequestMapping("/api/v1/productos-ventas")
public class ProductoVentaController {

    @Autowired
    private ProductoVentaService productoVentaService;

    @GetMapping
    public ResponseEntity<List<ProductoVenta>> getAllProductoVentas() {
        List<ProductoVenta> productosVentas = productoVentaService.findAll();
        if (productosVentas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosVentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoVenta> getProductoVentaById(@PathVariable Integer id) {
        ProductoVenta productoVenta = productoVentaService.findById(id);
        if (productoVenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoVenta);
    }

    @PostMapping
    public ResponseEntity<ProductoVenta> createProductoVenta(@RequestBody ProductoVenta productoVenta) {
        ProductoVenta newProductoVenta = productoVentaService.save(productoVenta);
        return ResponseEntity.status(201).body(newProductoVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoVenta> updateProductoVenta(@PathVariable Integer id, @RequestBody ProductoVenta productoVenta) {
        productoVenta.setId(id);
        ProductoVenta updatedProductoVenta = productoVentaService.save(productoVenta);
        return ResponseEntity.ok(updatedProductoVenta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductoVenta> partialUpdateProductoVenta(@PathVariable Integer id, @RequestBody ProductoVenta productoVenta) {
        productoVenta.setId(id);
        ProductoVenta updatedProductoVenta = productoVentaService.partialUpdate(productoVenta);

        if (updatedProductoVenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProductoVenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductoVenta(@PathVariable Integer id) {
        if (productoVentaService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        productoVentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productos/{productoId}")
    public ResponseEntity<List<ProductoVenta>> getByProductoId(@PathVariable Integer productoId) {
        List<ProductoVenta> lista = productoVentaService.findByProductoId(productoId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/productos/{productoId}")
    public ResponseEntity<Void> deleteByProductoId(@PathVariable Integer productoId) {
        productoVentaService.deleteByProductoId(productoId);
        return ResponseEntity.noContent().build();
    }


    //relaciones
    @GetMapping("/ventas/{ventaId}")
    public ResponseEntity<List<ProductoVenta>> getByVentaId(@PathVariable Integer ventaId) {
        List<ProductoVenta> lista = productoVentaService.findByVentaId(ventaId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/ventas/{ventaId}")
    public ResponseEntity<Void> deleteByVentaId(@PathVariable Integer ventaId) {
        productoVentaService.deleteByVentaId(ventaId);
        return ResponseEntity.noContent().build();
    }
}