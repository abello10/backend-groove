package com.proyecto.groove.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.proyecto.groove.service.ProductoService;
import com.proyecto.groove.assembler.ProductoModelAssembler;
import com.proyecto.groove.model.Producto;

@RestController
@RequestMapping("/api/v2/producto")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los productos", description = "Muestra todos los productos de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProducto() {
        List<EntityModel<Producto>> producto = productoService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (producto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            producto,
            linkTo(methodOn(ProductoControllerV2.class).getAllProducto()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar un producto", description = "Muestra un producto por ID")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un producto",description = "Permite crear un producto")
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity
            .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getId())).toUri())
            .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un producto", description = "Permite actualizar un producto ya creado")
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un producto", description = "Permite actualizar una linea de un producto")
    public ResponseEntity<EntityModel<Producto>> partialUpdateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.partialUpdate(producto);
        
        if (updatedProducto== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar un producto", description = "Permite borrar un producto por su ID")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/buscar",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar",description = "Busca un producto por nombre")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam String nombre){

        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping(value ="/buscarPorTipo",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar por tipo",description = "Busca un producto por tipo")
    public ResponseEntity<List<Producto>> buscarPorTipo(@RequestParam Integer tipo){
        return ResponseEntity.ok(productoService.buscarPorTipo(tipo));
    }

    @GetMapping(value ="/ordenar",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Ordenar ascendente/descendente",description = "Ordena el precio del producto de forma ascendente o descendente")
    public ResponseEntity<List<Producto>> listarProductosOrdenados(
        @RequestParam(defaultValue = "asc") String orden){
            List<Producto> productos = productoService.OrdenarPrecio(orden);
            return ResponseEntity.ok(productos);
        }
}
