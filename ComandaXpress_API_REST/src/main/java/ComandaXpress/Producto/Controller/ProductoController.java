package ComandaXpress.Producto.Controller;

import ComandaXpress.Categoria.Model.Categoria;
import ComandaXpress.Categoria.Repository.CategoriaRepository;
import ComandaXpress.Producto.Model.Producto;
import ComandaXpress.Producto.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos") // Ruta base para los productos
public class ProductoController {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Crear un nuevo producto
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {

        // Suponiendo que el cuerpo de la solicitud contiene el 'categoriaId' para asignar
        Long categoriaId = producto.getCategoria().getCategoriaId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));

        producto.setCategoria(categoria);
        return productoRepository.save(producto); // Guardar el producto con su categoría en la base de datos
    }

    // Obtener un producto por su ID
    @GetMapping("/{productoId}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return ResponseEntity.ok(producto);
    }

    // Actualizar un producto existente
    @PutMapping("/{productoId}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long productoId, @RequestBody Producto detallesProducto) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setNombre(detallesProducto.getNombre());
        producto.setPrecio(detallesProducto.getPrecio());
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setCategoria(detallesProducto.getCategoria());
        producto.setImagenUrl(detallesProducto.getImagenUrl());
        producto.setActivo(detallesProducto.isActivo());
        final Producto productoActualizado = productoRepository.save(producto);

        return ResponseEntity.ok(productoActualizado);
    }

    // Borrar un producto
    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
        return ResponseEntity.ok().build();
    }
}
