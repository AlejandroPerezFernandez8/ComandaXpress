package ComandaXpress.Producto.Controller;

import ComandaXpress.Categoria.Model.Categoria;
import ComandaXpress.Categoria.Repository.CategoriaRepository;
import ComandaXpress.DTO.MesaDTO;
import ComandaXpress.DTO.ProductoDTO;
import ComandaXpress.Mesa.Model.Mesa;
import ComandaXpress.Producto.Model.Producto;
import ComandaXpress.Producto.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos") // Ruta base para los productos
public class ProductoController {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream().map(ProductoDTO::converter).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setProductoId(producto.getProductoId());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setActivo(productoDTO.isActivo());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setImagenUrl(productoDTO.getImagenUrl());
        producto.setCategoria(categoriaRepository.findById(productoDTO.categoriaId).
                orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada con ID: " + productoDTO.getCategoriaId())));


        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long productoId) {
        Optional<Producto> producto = productoRepository.findById(productoId);
        return producto.map(c -> ResponseEntity.ok(ProductoDTO.converter(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long productoId, @RequestBody ProductoDTO detallesProducto) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setNombre(detallesProducto.getNombre());
        producto.setPrecio(detallesProducto.getPrecio());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setImagenUrl(detallesProducto.getImagenUrl());
        producto.setActivo(detallesProducto.isActivo());
        Categoria categoria = categoriaRepository.findById(detallesProducto.getCategoriaId())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada con ID: " + detallesProducto.getCategoriaId()));

        final Producto productoActualizado = productoRepository.save(producto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
        return ResponseEntity.ok().build();
    }
}
