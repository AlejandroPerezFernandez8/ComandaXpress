package ComandaXpress.Categoria.Controller;

import ComandaXpress.Categoria.Model.Categoria;
import ComandaXpress.Categoria.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public String guardarCategoria(@RequestBody Categoria categoria) {
        categoriaRepository.save(categoria);
        return "Categoría guardada con éxito!";
    }

    @PutMapping("/{categoriaId}")
    public String actualizarCategoria(@PathVariable Long categoriaId, @RequestBody Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")); //Excepcion
        categoriaExistente.setNombre(categoria.getNombre());
        categoriaExistente.setDescripcion(categoria.getDescripcion());

        categoriaRepository.save(categoriaExistente);
        return "Categoría actualizada con éxito!";
    }

    @DeleteMapping("/{categoriaId}")
    public String eliminarCategoria(@PathVariable Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")); // Excepcion
        categoriaRepository.delete(categoria);
        return "Categoría eliminada con éxito!";
    }
}
